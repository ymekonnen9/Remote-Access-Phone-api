package com.remoteAccess.backend.controller;

import com.remoteAccess.backend.enumeration.FileType;
import com.remoteAccess.backend.response.StandardResponse;
import com.remoteAccess.backend.services.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/s3bucketstorage", method = {RequestMethod.GET, RequestMethod.POST})
public class AwsController {

    @Autowired
    private AwsService service;

    @GetMapping("/{bucketName}")
    public ResponseEntity<StandardResponse<List<String>>> listFiles(@PathVariable("bucketName") String bucketName) {
        try {
            List<String> files = service.listFiles(bucketName); // Explicitly declare type
            return ResponseEntity.ok(new StandardResponse<>(true, "Files listed successfully", files));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new StandardResponse<>(false, "Failed to list files: " + e.getMessage(), null));
        }
    }

    @PostMapping("/{bucketName}/upload")
    public ResponseEntity<StandardResponse<String>> uploadFile(
            @PathVariable("bucketName") String bucketName,
            @RequestParam("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new StandardResponse<>(false, "File is empty", null));
        }

        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String contentType = file.getContentType();
            long fileSize = file.getSize();
            InputStream inputStream = file.getInputStream();

            service.uploadFile(bucketName, fileName, fileSize, contentType, inputStream);

            return ResponseEntity.ok(new StandardResponse<>(true, "File uploaded successfully", fileName));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(
                    new StandardResponse<>(false, "Error reading file: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new StandardResponse<>(false, "Failed to upload file: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{bucketName}/download/{fileName}")
    public ResponseEntity<?> downloadFile(
            @PathVariable("bucketName") String bucketName,
            @PathVariable("fileName") String fileName
    ) {
        try {
            ByteArrayOutputStream body = service.downloadFile(bucketName, fileName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(FileType.fromFilename(fileName))
                    .body(body.toByteArray());
        } catch (IOException e) {
            return ResponseEntity.status(500).body(
                    new StandardResponse<>(false, "Error downloading file: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new StandardResponse<>(false, "Failed to download file: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{bucketName}/{fileName}")
    public ResponseEntity<StandardResponse<String>> deleteFile(
            @PathVariable("bucketName") String bucketName,
            @PathVariable("fileName") String fileName
    ) {
        try {
            service.deleteFile(bucketName, fileName);
            return ResponseEntity.ok(new StandardResponse<>(true, "File deleted successfully", fileName));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new StandardResponse<>(false, "Failed to delete file: " + e.getMessage(), null));
        }
    }
}
