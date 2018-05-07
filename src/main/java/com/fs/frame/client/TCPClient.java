package com.fs.frame.client;


import com.fs.frame.beans.ImageFrame;
import com.fs.frame.client.capture.CVCapture;
import com.fs.frame.common.services.CommonClient;
import com.fs.frame.common.services.ImageFrameService;
import com.fs.frame.common.utills.BufferUtills;
import com.fs.frame.common.utills.CompressUtils;
import org.bytedeco.javacv.FrameGrabber;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class TCPClient extends CommonClient {
//    private CVCapture capture = (CVCapture) CaptureFactory.get().getCapture(CVCapture.class);
    private CVCapture capture = new CVCapture(1920,1080);
    private ImageFrameService frameService = new ImageFrameService();
    public TCPClient(String host, int port ) throws IOException {
        super(host, port);
        super.init();
    }
    @Override
    protected void write(SocketChannel channel) throws IOException {
        frameService.writeImageFrame(channel,capture.captureFrame());
    }

    public static void main(String[] args) throws IOException {
        TCPClient tcpClient = new TCPClient("localhost", 12321);
        tcpClient.start();

    }

}
