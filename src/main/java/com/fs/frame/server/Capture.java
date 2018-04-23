package com.fs.frame.server;

import org.bytedeco.javacv.FrameGrabber;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Capture {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    BufferedImage captureScreen2Image() throws FrameGrabber.Exception;
}
