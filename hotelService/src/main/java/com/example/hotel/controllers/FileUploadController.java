package com.example.hotel.controllers;

import com.example.hotel.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/files")
public class FileUploadController {
    @Autowired
    private S3Service s3Service;
    @Value("${aws.s3.bucket}")
    private String bucketName;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,String hotelId) {
        try {
            String fileUrl = s3Service.uploadFile(file, bucketName,hotelId);
            return new ResponseEntity<>(fileUrl, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{key}")
    public ResponseEntity<InputStreamResource> fetchFile(@PathVariable String key) {
        InputStream fileStream = s3Service.fetchFileStream(bucketName, key);
        InputStreamResource resource = new InputStreamResource(fileStream);

        MediaType contentType = getContentType(key);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + key + "\"")
                .contentType(contentType)
                .body(resource);
    }

    public MediaType getContentType(String key) {
        if (key.endsWith(".jpg") || key.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (key.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (key.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}

