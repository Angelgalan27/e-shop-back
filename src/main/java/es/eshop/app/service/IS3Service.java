package es.eshop.app.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;

public interface IS3Service {

    Boolean updateFile(@NotNull MultipartFile file,@NotNull String path);

    InputStreamResource getFile(@NotNull String path);

    DeleteObjectResponse deleteFile(@NotNull String path);
}
