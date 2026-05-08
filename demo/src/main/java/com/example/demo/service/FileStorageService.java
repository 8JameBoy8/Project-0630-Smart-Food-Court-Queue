package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.FileStorageProperties;

@Service
public class FileStorageService {

   
    private String uploadDir;

    public FileStorageService(FileStorageProperties properties) {
        this.uploadDir = properties.getUploadDir();
    }

    // ประเภทไฟล์ที่รับได้
    private static final List<String> ALLOWED_TYPES = List.of(
        "image/jpeg",
        "image/png",
        "image/jpg"
    );

    // ขนาดไฟล์สูงสุด 5MB
    private static final long MAX_SIZE = 5 * 1024 * 1024;

    public String saveFile(MultipartFile file) throws IOException {

        // เช็คว่าไฟล์ว่างเปล่าไหม
        if (file.isEmpty()) {
            throw new IllegalArgumentException("ไม่พบไฟล์");
        }

        // เช็คประเภทไฟล์
        String contentType = file.getContentType();
        if (!ALLOWED_TYPES.contains(contentType)) {
            throw new IllegalArgumentException(
                "ประเภทไฟล์ไม่ถูกต้อง รับเฉพาะ JPG และ PNG เท่านั้น"
            );
        }

        // เช็คขนาดไฟล์
        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException(
                "ไฟล์ใหญ่เกินไป ขนาดสูงสุด 5MB"
            );
        }

        // บันทึกไฟล์
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return filePath.toString();
    }
}
