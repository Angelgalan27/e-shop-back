package es.eshop.app.service.impl;

import es.eshop.app.exception.GenericException;
import es.eshop.app.service.IS3Service;
import es.eshop.app.util.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl implements IS3Service {

    @Value("${aws.bucketName}")
    private String bucketName;

    private final S3Client s3Client;

    @Override
    public Boolean updateFile(MultipartFile file, String path) {
        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(path)
                    .build(), RequestBody.fromBytes(file.getBytes()));
            return Boolean.TRUE;
        } catch (IOException e) {
            log.error("UPDATE FILE ERROR: " + e.getMessage());
            throw new GenericException(Resource.getMessage("s3.upload.file.error"));
        }
    }

    @Override
    public InputStreamResource getFile(String path) {
        ResponseInputStream<GetObjectResponse> result = s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(path)
                .build());

        return new InputStreamResource(result);
    }

    @Override
    public DeleteObjectResponse deleteFile(String path) {
        return s3Client.deleteObject(DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(path)
                .build());
    }
}
