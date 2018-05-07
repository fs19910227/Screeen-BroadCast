package com.fs.frame.common.utills;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;

public class BufferUtills {
    /**

     * @throws IOException
     */
    /**
     * 保证读完所有数据
     *
     * @param channel
     * @param buffer
     * @return
     * @throws IOException
     */
    public static boolean fullRead(SocketChannel channel, ByteBuffer buffer) throws IOException {
        int head = 0;
        while (buffer.hasRemaining()) {
            head = channel.read(buffer);
            if (head == -1) {

                throw new ClosedByInterruptException();
            }
        }
        buffer.flip();
        return true;
    }

    /**
     * @param channel
     * @param buffer
     * @return
     */
    public static boolean fullWrite(SocketChannel channel, ByteBuffer buffer) throws IOException {
        int head = 0;
        while (buffer.hasRemaining()) {
            head = channel.write(buffer);
            if (head == -1) {

                throw new ClosedByInterruptException();
            }
        }
        buffer.flip();
        return true;
    }
    public static boolean fullWrite(SocketChannel channel, ByteBuffer...buffers) throws IOException {
        for (ByteBuffer buffer : buffers) {
            int head;
            while (buffer.hasRemaining()) {
                head = channel.write(buffer);
                if (head == -1) {

                    throw new ClosedByInterruptException();
                }
            }
            buffer.flip();
        }
        return true;
    }

    /**
     * @param channel
     * @param buffer
     * @return
     */
    public static boolean incrementWrite(SocketChannel channel, ByteBuffer buffer) throws IOException {
        int head = 0;
        head = channel.write(buffer);
        if (head == -1) {
            throw new ClosedByInterruptException();
        }
        boolean remaining = buffer.hasRemaining();
        if(!remaining) buffer.flip();
        return remaining;
    }
    public static boolean incrementWrite(SocketChannel channel, ByteBuffer...buffers) throws IOException {
        for (ByteBuffer buffer : buffers) {
            int head;
            head = channel.write(buffer);
            if (head == -1) {
                throw new ClosedByInterruptException();
            }
            if(buffer.hasRemaining()){
                return false;
            }
        }
        for (ByteBuffer buffer : buffers) {
            buffer.flip();
        }
        return true;
    }
}
