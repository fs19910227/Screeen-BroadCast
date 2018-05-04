package com.fs.frame.client;


import com.fs.frame.beans.ImageFrame;
import com.fs.frame.client.capture.CVCapture;
import com.fs.frame.client.capture.CaptureFactory;
import com.fs.frame.common.CommonClient;
import com.fs.frame.common.utills.BufferUtills;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class TCPClient extends CommonClient {
    private CVCapture capture = (CVCapture) CaptureFactory.get().getCapture(CVCapture.class);

    public TCPClient(String host, int port ) throws IOException {
        super(host, port);
        super.init();
    }

    @Override
    protected void write(SocketChannel channel) throws IOException {
        long start = System.currentTimeMillis();
        ImageFrame imageFrame = capture.captureFrame();
        try{
            ByteBuffer headBuffer = imageFrame.headBuffer();
            ByteBuffer dataBuffer = imageFrame.dataBuffer();
            BufferUtills.fullWrite(channel,headBuffer);
            BufferUtills.fullWrite(channel,dataBuffer);
        }finally{
            imageFrame.clear();
            long costTime = System.currentTimeMillis() - start;
            System.out.printf("grab and send cost time=%sms\r\n", costTime);
        }

    }

    public static void main(String[] args) throws IOException {
        TCPClient tcpClient = new TCPClient("localhost", 8888).init();
        tcpClient.start();

    }

}
