package com.fs.frame.client.capture;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class RobotCapture implements Capture {
    private Robot robot = null;
    public RobotCapture() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    @Override
    public BufferedImage captureScreen2Image() {
        final Rectangle rectangle = new Rectangle(0,0,1366,768);
        BufferedImage image = robot.createScreenCapture(rectangle);
        return image;
    }



}
