package com.fs.frame.beans;

import org.bytedeco.javacv.Frame;

import java.io.Serializable;
import java.util.Arrays;

public class ImageFrame extends Frame implements Serializable {
    /**
     *
     */
    public int imageCapacity;
    public int dataLength;

    @Override
    public String toString() {
        return "ImageFrameService{" +
                "imageWidth=" + imageWidth +
                ", imageHeight=" + imageHeight +
                ", imageDepth=" + imageDepth +
                ", imageChannels=" + imageChannels +
                ", imageStride=" + imageStride +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
