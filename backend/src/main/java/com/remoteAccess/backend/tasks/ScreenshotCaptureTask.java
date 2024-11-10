package com.remoteAccess.backend.tasks;

import com.remoteAccess.backend.services.AwsService;
import com.remoteAccess.backend.utils.ScreenshotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.remoteAccess.backend.services.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScreenshotCaptureTask {

    @Autowired
    private AwsService awsService;

    @Value("${s3.bucket.name}")
    private String bucketName;

    // Method to capture and upload screenshot (for reference)
    public void captureAndUploadScreenshot() {
        try {
            // Implement your screenshot capture logic here
            // For example, capturing and uploading a screenshot to S3
            System.out.println("This method is used for screenshots, but not used for videos.");
        } catch (Exception e) {
            System.err.println("Error capturing or uploading screenshot: " + e.getMessage());
        }
    }

    // New method to handle uploaded video from frontend
    public void captureAndUploadVideo(MultipartFile videoFile) throws IOException {
        try {
            // Get the video file input stream
            InputStream videoStream = videoFile.getInputStream();

            // Get the file name and timestamp to prevent overwriting
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "video_" + timestamp + ".webm";  // Use the appropriate file extension (.webm or .mp4)

            // Get the content length of the video file
            long contentLength = videoFile.getSize();

            // Upload video to AWS S3 or another storage service
            awsService.uploadFile(bucketName, fileName, contentLength, "video/webm", videoStream);
            System.out.println("Video uploaded: " + fileName);

        } catch (Exception e) {
            System.err.println("Error capturing or uploading video: " + e.getMessage());
            throw new IOException("Error uploading video: " + e.getMessage(), e);
        }
    }
}
