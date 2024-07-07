package com.example.hotel.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface S3Service {
    public String uploadFile(MultipartFile file,String bucketName,String hotelId) throws IOException;
    public InputStream fetchFileStream(String bucketName, String key);
}
