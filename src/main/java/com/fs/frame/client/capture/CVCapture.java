package com.fs.frame.client.capture;

import com.fs.frame.beans.ImageFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;

public class CVCapture implements Capture {
    private FFmpegFrameGrabber grabber;
    private Java2DFrameConverter converter = new Java2DFrameConverter();
    public CVCapture() throws FrameGrabber.Exception {
        int x = 0, y = 0, w = 1024, h = 768;
        grabber = new FFmpegFrameGrabber(":0.0+" + x + "," + y);
        grabber.setFormat("x11grab");
        grabber.setImageWidth(screenSize.width);
        grabber.setImageHeight(screenSize.height);
        grabber.start();
    }

    @Override
    public BufferedImage captureScreen2Image() throws FrameGrabber.Exception {
        Frame grab = grabber.grabImage();
        BufferedImage bufferedImage = converter.getBufferedImage(grab);
        return bufferedImage;
    }
    public ImageFrame captureFrame() throws FrameGrabber.Exception {
        Frame grab = grabber.grabImage();
        return new ImageFrame(grab);
    }
}
