package com.remoteAccess.backend.services;

import org.springframework.stereotype.Service;

@Service
public class UserSessionService {

    private boolean captureScreenshots = false;

    public void activateScreenshotCapture() {
        captureScreenshots = true;
    }

    public void deactivateScreenshotCapture() {
        captureScreenshots = false;
    }

    public boolean isCaptureScreenshots() {
        return captureScreenshots;
    }
}
