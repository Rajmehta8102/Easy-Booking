package com.example.hotel.services.impl;

import com.example.hotel.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class S3ServiceImpl implements S3Service {
    @Autowired
    private S3Client s3Client;
    @Override
    public String uploadFile(MultipartFile file, String bucketName, String hotelId) throws IOException {
        String fileName = hotelId+".jpeg";
        Path tempFile = Path.of(System.getProperty("java.io.tmpdir"), fileName);
        file.transferTo(tempFile);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        PutObjectResponse response = s3Client.putObject(putObjectRequest, tempFile);

        tempFile.toFile().delete();

        return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toExternalForm();
    }
    @Override
    public InputStream fetchFileStream(String bucketName, String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        return s3Client.getObject(getObjectRequest);
    }
}
