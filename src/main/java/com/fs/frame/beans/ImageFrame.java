package com.fs.frame.beans;

import com.fs.frame.common.utills.BufferUtills;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import java.awt.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ImageFrame extends Frame implements Serializable {
    public int imageCapacity;
    ByteBuffer headBuffer = ByteBuffer.allocate(12);
    ByteBuffer dataBuffer;

    public ImageFrame(Frame old) {
        this.imageWidth = old.imageWidth;
        this.imageHeight = old.imageHeight;
        this.imageChannels = old.imageChannels;
        this.imageDepth = old.imageDepth;
        this.imageStride = old.imageStride;
        this.image = old.image;
        this.imageCapacity = old.image[0].capacity();
    }

    public ImageFrame() {
    }

    public ImageFrame readData(SocketChannel channel) throws IOException {
        BufferUtills.fullRead(channel, headBuffer);
        this.imageWidth = headBuffer.getShort();
        this.imageHeight = headBuffer.getShort();
        this.imageChannels = headBuffer.get();
        this.imageDepth = headBuffer.get();
        this.imageStride = headBuffer.getShort();
        this.imageCapacity = headBuffer.getInt();
        if (this.dataBuffer == null) {
            dataBuffer = ByteBuffer.allocate(this.imageCapacity);
        }
        BufferUtills.fullRead(channel, dataBuffer);
        this.image = new ByteBuffer[]{dataBuffer};
        return this;
    }

    public ByteBuffer headBuffer() throws IOException {
        headBuffer.clear();
        headBuffer.putShort((short) imageWidth);
        headBuffer.putShort((short) imageHeight);
        headBuffer.put((byte) imageChannels);
        headBuffer.put((byte) imageDepth);
        headBuffer.putShort((short) imageStride);
        headBuffer.putInt(imageCapacity);
        headBuffer.flip();
        return headBuffer;
    }

    public ByteBuffer dataBuffer() {
        ByteBuffer byteBuffer = (ByteBuffer) image[0];
        return byteBuffer;
    }


    public void clear() {
        headBuffer.clear();
        dataBuffer().clear();
    }

    @Override
    public String toString() {
        return "ImageFrame{" +
                "imageWidth=" + imageWidth +
                ", imageHeight=" + imageHeight +
                ", imageDepth=" + imageDepth +
                ", imageChannels=" + imageChannels +
                ", imageStride=" + imageStride +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
