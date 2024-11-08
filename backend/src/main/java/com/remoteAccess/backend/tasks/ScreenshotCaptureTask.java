package com.remoteAccess.backend.tasks;

import com.remoteAccess.backend.services.AwsService;
import com.remoteAccess.backend.services.UserSessionService;
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

@Component
public class ScreenshotCaptureTask {

    @Autowired
    private AwsService awsService;

    @Value("${s3.bucket.name}")
    private String bucketName;

    @Autowired
    private UserSessionService userSessionService;

    @Scheduled(fixedRate = 60000)
    public void captureAndUploadScreenshot() {
        if (!userSessionService.isCaptureScreenshots()) {
            return;
        }
        try {
            BufferedImage screenshot = ScreenshotUtil.captureScreenshot();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(screenshot, "png", os);
            InputStream screenshotStream = new ByteArrayInputStream(os.toByteArray());

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "screenshot_" + timestamp + ".png";
            long contentLength = os.size();

            awsService.uploadFile(bucketName, fileName, contentLength, "image/png", screenshotStream);
            System.out.println("Screenshot uploaded: " + fileName);

        } catch (Exception e) {
            System.err.println("Error capturing or uploading screenshot: " + e.getMessage());
        }
    }
}
