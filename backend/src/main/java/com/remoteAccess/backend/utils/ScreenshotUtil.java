package com.remoteAccess.backend.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenshotUtil {

    public static BufferedImage captureScreenshot() throws AWTException {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        Robot robot = new Robot();
        return robot.createScreenCapture(screenRect);
    }
}
