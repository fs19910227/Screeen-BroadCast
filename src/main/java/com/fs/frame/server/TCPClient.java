package com.fs.frame.server;


import com.fs.frame.beans.ImageFrame;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class TCPClient {
    CVCapture capture = (CVCapture) CaptureFactory.get().getCapture(CVCapture.class);
    String host;
    int port;
    int delay;
    SocketChannel channel = SocketChannel.open();

    public TCPClient(String host, int port, int delay) throws IOException {
        this.host = host;
        this.port = port;
        this.delay = delay;
        channel.connect(new InetSocketAddress(host, port));
    }


    public void newCatchAndSend() throws IOException {

        long start = System.currentTimeMillis();
        ImageFrame imageFrame = capture.captureFrame();
        ByteBuffer headBuffer = imageFrame.headBuffer();
        ByteBuffer dataBuffer = imageFrame.dataBuffer();
        channel.write(headBuffer);
        channel.write(dataBuffer);
        imageFrame.clear();
        long costTime = System.currentTimeMillis() - start;
        System.out.printf("grab and send cost time=%sms\r\n", costTime);
    }


    public static void main(String[] args) throws IOException {
        TCPClient tcpClient = new TCPClient("localhost", 8888, 0);
        tcpClient.newCatchAndSend();

    }

    public void start() {
        while (true) {
            try {
                long start = System.currentTimeMillis();
                this.newCatchAndSend();
                long costTime = System.currentTimeMillis() - start;
                System.out.printf("grab and send cost time=%sms\r\n", costTime);
                Thread.sleep(delay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
