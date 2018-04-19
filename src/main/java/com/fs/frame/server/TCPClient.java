package com.fs.frame.server;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
    String host;
    int port;
    Capture capture = Capture.getCapture();
    int delay;

    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public TCPClient(String host, int port, int delay) {
        this.host = host;
        this.port = port;
        this.delay = delay;
    }

    public void catchAndSend() throws IOException {
        BufferedImage image = capture.captureScreen();
        Socket socket = new Socket(host, port);
        OutputStream outputStream = socket.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        socket.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Integer port = Integer.valueOf(args[1]);
        String host = String.valueOf(args[0]);
        int delay=Integer.valueOf(args[2]);
        TCPClient tcpClient = new TCPClient(host, port,delay);
        while (true) {
            tcpClient.catchAndSend();
            Thread.sleep(900);
        }

    }

    public void start() {
        while (true) {
            try {
                long start=System.currentTimeMillis();
                this.catchAndSend();
                long end=System.currentTimeMillis()-start;
                System.out.println("cost time:"+end+"ms");
                Thread.sleep(delay);
            } catch (Exception  e) {
                e.printStackTrace();
            }
        }
    }
}
