package com.fs.frame.client.capture;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;

public class CVCapture implements Capture {
    private FFmpegFrameGrabber grabber;
    private Java2DFrameConverter converter = new Java2DFrameConverter();

    public CVCapture(int width, int height) throws FrameGrabber.Exception {
        int x = 0, y = 0;
        grabber = new FFmpegFrameGrabber(":0.0+" + x + "," + y);
        grabber.setFormat("x11grab");
        grabber.setImageWidth(width);
        grabber.setImageHeight(height);
        grabber.start();
    }

    public CVCapture() throws FrameGrabber.Exception {
        this(screenSize.width, screenSize.width);
    }

    @Override
    public BufferedImage captureScreen2Image() throws FrameGrabber.Exception {
        Frame grab = grabber.grabImage();
        return converter.getBufferedImage(grab);
    }

    public Frame captureFrame() throws FrameGrabber.Exception {
        Frame grab = grabber.grabImage();
        return grab;
    }
}
