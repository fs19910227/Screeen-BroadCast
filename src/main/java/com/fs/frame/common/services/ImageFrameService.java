package com.fs.frame.common.services;

import com.fs.frame.beans.ImageFrame;
import com.fs.frame.common.utills.BufferUtills;
import com.fs.frame.common.utills.CompressUtils;
import org.bytedeco.javacv.Frame;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.zip.DataFormatException;

public class ImageFrameService {
    /**
     *
     */
    public static final int HEAD_LENGTH = 16;

    /**
     * create ImageFrame from grab frame
     *
     * @param old
     * @return
     */
    private ImageFrame ImageFrameFromOld(Frame old) {
        ImageFrame frame = new ImageFrame();
        frame.imageWidth = old.imageWidth;
        frame.imageHeight = old.imageHeight;
        frame.imageChannels = old.imageChannels;
        frame.imageDepth = old.imageDepth;
        frame.imageStride = old.imageStride;
        frame.image = old.image;
        frame.imageCapacity = old.image[0].capacity();
        return frame;
    }

    /**
     * read frame data from channel
     *
     * @param channel
     * @return
     * @throws IOException
     * @throws DataFormatException
     */
    public ImageFrame readImageFrame(SocketChannel channel) throws IOException, DataFormatException {
        ImageFrame frame = resolveHead(channel);
        ByteBuffer tempBuffer = ByteBuffer.allocate(frame.dataLength);
        BufferUtills.fullRead(channel, tempBuffer);
        ByteBuffer byteBuffer = CompressUtils.deComressByteBuffer(tempBuffer, frame.imageCapacity);
        frame.image = new ByteBuffer[]{byteBuffer};
        return frame;
    }

    /**
     * write frame data to channel
     *
     * @param channel
     * @return
     */
    public void writeImageFrame(SocketChannel channel, Frame source) throws IOException {
        ByteBuffer sourceImage = (ByteBuffer) source.image[0];
        ByteBuffer dataBuffer = CompressUtils.comressByteBuffer(sourceImage);
        ByteBuffer headBuffer = genHeadBuffer(source, dataBuffer.capacity());
        BufferUtills.fullWrite(channel, headBuffer, dataBuffer);
    }

    /**
     * 解析消息头
     *
     * @param channel
     * @return
     * @throws IOException
     */
    private ImageFrame resolveHead(SocketChannel channel) throws IOException {
        ImageFrame frame = new ImageFrame();
        ByteBuffer headBuffer = ByteBuffer.allocate(HEAD_LENGTH);
        BufferUtills.fullRead(channel, headBuffer);
        frame.imageWidth = headBuffer.getShort();
        frame.imageHeight = headBuffer.getShort();
        frame.imageChannels = headBuffer.get();
        frame.imageDepth = headBuffer.get();
        frame.imageStride = headBuffer.getShort();
        frame.imageCapacity = headBuffer.getInt();
        frame.dataLength = headBuffer.getInt();
        return frame;
    }

    /**
     * 生成消息头
     *
     * @param source
     * @param compressDataLength
     * @return
     */
    private ByteBuffer genHeadBuffer(Frame source, int compressDataLength) {
        ByteBuffer headBuffer = ByteBuffer.allocate(HEAD_LENGTH);
        headBuffer.putShort((short) source.imageWidth);
        headBuffer.putShort((short) source.imageHeight);
        headBuffer.put((byte) source.imageChannels);
        headBuffer.put((byte) source.imageDepth);
        headBuffer.putShort((short) source.imageStride);
        headBuffer.putInt(source.image[0].capacity());
        headBuffer.putInt(compressDataLength);
        headBuffer.flip();
        return headBuffer;
    }


}
