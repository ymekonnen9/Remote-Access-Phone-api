package com.remoteAccess.backend.services;
import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.remoteAccess.backend.exception.FileDeleteException;
import com.remoteAccess.backend.exception.FileDownloadException;
import com.remoteAccess.backend.exception.FileUploadException;
import com.remoteAccess.backend.models.ScreenShotFile;
import com.remoteAccess.backend.repositories.ScreenShotFileRepository;
import com.remoteAccess.backend.services.AwsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AwsServiceImplementation implements AwsService {

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private ScreenShotFileRepository screenShotFileRepository;

    @Override
    public void uploadFile(
            final String bucketName,
            final String keyName,
            final Long contentLength,
            final String contentType,
            final InputStream value
    ) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(contentLength);
            metadata.setContentType(contentType);

            s3Client.putObject(bucketName, keyName, value, metadata);
            log.info("File uploaded to bucket({}): {}", bucketName, keyName);

            String s3Url = s3Client.getUrl(bucketName, keyName).toString();

            ScreenShotFile screenShotFile = new ScreenShotFile();
            screenShotFile.setS3Url(s3Url);
            screenShotFile.setUserId(1);
            screenShotFile.setTimestamp(LocalDateTime.now());

            screenShotFileRepository.save(screenShotFile);
            log.info("Screenshot metadata saved in database with URL: {}", s3Url);
        } catch (AmazonClientException e) {
            log.error("Error uploading file to bucket({}): {}", bucketName, keyName, e);
            throw new FileUploadException("Could not upload file to S3", e);
        }
    }

    @Override
    public ByteArrayOutputStream downloadFile(
            final String bucketName,
            final String keyName
    ) {
        try {
            S3Object s3Object = s3Client.getObject(bucketName, keyName);
            InputStream inputStream = s3Object.getObjectContent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[4096];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            log.info("File downloaded from bucket({}): {}", bucketName, keyName);
            return outputStream;
        } catch (AmazonClientException | IOException e) {
            log.error("Error downloading file from bucket({}): {}", bucketName, keyName, e);
            throw new FileDownloadException("Could not download file from S3", e);
        }
    }

    @Override
    public List<String> listFiles(String bucketName) throws AmazonClientException {
        return List.of();
    }

    @Override
    public void deleteFile(
            final String bucketName,
            final String keyName
    ) {
        try {
            s3Client.deleteObject(bucketName, keyName);
            log.info("File deleted from bucket({}): {}", bucketName, keyName);
        } catch (AmazonClientException e) {
            log.error("Error deleting file from bucket({}): {}", bucketName, keyName, e);
            throw new FileDeleteException("Could not delete file from S3", e);
        }
    }
}