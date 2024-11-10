package com.remoteAccess.backend.controller;

import com.remoteAccess.backend.services.AwsServiceImplementation;
import com.remoteAccess.backend.tasks.ScreenshotCaptureTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/screen")
public class ScreenShotController {

    private ScreenshotCaptureTask screenshotCaptureTask;
    @Autowired
    public  ScreenShotController(ScreenshotCaptureTask screenshotCaptureTask){
        this.screenshotCaptureTask = screenshotCaptureTask;
    }
    @PostMapping("/uploadVideo")
    public ResponseEntity<Map<String, String>> uploadVideo(@RequestParam("video") MultipartFile videoFile) {
        Map<String, String> response = new HashMap<>();

        try {
            // Process the video file here (e.g., save it, upload to S3, etc.)
            String videoFileName = videoFile.getOriginalFilename();
            long fileSize = videoFile.getSize();

            // Save video to file system or upload to cloud storage (e.g., S3)
            // For example: saveFile(videoFile);
            screenshotCaptureTask.captureAndUploadVideo(videoFile);
            response.put("message", "Video uploaded successfully");
            response.put("fileName", videoFileName);
            response.put("fileSize", String.valueOf(fileSize));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Failed to upload video: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
