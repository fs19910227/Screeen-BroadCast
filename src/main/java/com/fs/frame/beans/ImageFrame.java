package com.fs.frame.beans;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import java.awt.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

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
        this.imageCapacity=old.image[0].capacity();
    }

    public ImageFrame() {
    }

    public ImageFrame readData(SocketChannel channel) throws IOException {
        channel.read(headBuffer);
        headBuffer.flip();
        this.imageWidth=headBuffer.getShort();
        this.imageHeight=headBuffer.getShort();
        this.imageChannels=headBuffer.get();
        this.imageDepth=headBuffer.get();
        this.imageStride=headBuffer.getShort();
        this.imageCapacity=headBuffer.getInt();
        if(this.dataBuffer==null){
            dataBuffer=ByteBuffer.allocate(this.imageCapacity);
        }
        while (dataBuffer.hasRemaining()) {
            channel.read(dataBuffer);
        }
        dataBuffer.flip();
        this.image=new ByteBuffer[]{dataBuffer};
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

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream bufferOut = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        headToBytes(outputStream, objectOutputStream);

        ByteBuffer buffer = (ByteBuffer) this.image[0];

        for (int i = 0; i < buffer.limit(); i++) {
            byte b = buffer.get();
            bufferOut.write(b);

        }
        buffer.clear();
        objectOutputStream.writeObject(bufferOut.toByteArray());
        return outputStream.toByteArray();
    }

    private byte[] headToBytes(ByteArrayOutputStream outputStream, ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.imageWidth);
        objectOutputStream.writeObject(this.imageHeight);
        objectOutputStream.writeObject(this.imageChannels);
        objectOutputStream.writeObject(this.imageDepth);
        objectOutputStream.writeObject(this.imageStride);
        objectOutputStream.writeObject(this.imageCapacity);
        return outputStream.toByteArray();
    }

    public ImageFrame(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        this.imageWidth = (int) objectInputStream.readObject();
        this.imageHeight = (int) objectInputStream.readObject();
        this.imageChannels = (int) objectInputStream.readObject();
        this.imageDepth = (int) objectInputStream.readObject();
        this.imageStride = (int) objectInputStream.readObject();
        this.imageCapacity=(int)objectInputStream.readObject();
        byte[] buffer = (byte[]) objectInputStream.readObject();
        ByteBuffer wrap = ByteBuffer.wrap(buffer);
        this.image = new ByteBuffer[]{wrap};
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        CanvasFrame canvasFrame = new CanvasFrame("Screen RobotCapture");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("");
        grabber.setFormat("x11grab");
        grabber.setImageWidth(screenSize.width);
        grabber.setImageHeight(screenSize.height);
        grabber.setImageMode(FrameGrabber.ImageMode.COLOR);
//        grabber.grabImage();
        grabber.start();
        Frame frame = grabber.grabImage();
        ImageFrame myFrame = new ImageFrame(frame);
        System.out.println(myFrame.headBuffer());
//        for (; ; ) {
//            Frame frame = grabber.grabImage();
//            ImageFrame myFrame = new ImageFrame(frame);
//            byte[] x = myFrame.toBytes();
//            ImageFrame x1 = new ImageFrame(x);
//            System.out.println(x1);
//            canvasFrame.showImage(x1);
//        }
    }

    public void clear() {
        headBuffer.clear();
        dataBuffer().clear();
    }
}
