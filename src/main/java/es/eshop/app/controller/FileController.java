package es.eshop.app.controller;

import es.eshop.app.service.IS3Service;
import es.eshop.app.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final IS3Service service;

    @GetMapping("/getfile")
    public ResponseEntity<InputStreamResource> getFile(@RequestParam("path")String path) {
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path )
                .contentType(StringUtil.getExtension(path))
                .body(service.getFile(path));
    }
}
