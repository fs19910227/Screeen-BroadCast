package com.fs.frame.server;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class Capture {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Rectangle screenRectangle = new Rectangle(screenSize);
//    private Capture(){}
//    public static Capture =new Capture();
    private static Capture capture =new Capture();
    private Capture() {}
    public static Capture getCapture(){
        return capture;
    }
    public Dimension getScreenSize() {
        return screenSize;
    }

    public Rectangle getScreenRectangle() {
        return screenRectangle;
    }

    public BufferedImage captureScreen() {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        return image;
    }

}
