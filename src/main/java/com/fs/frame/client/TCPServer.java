package com.fs.frame.client;

import com.fs.frame.server.Capture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public ClientFrame ui;
    public ServerSocket serverSocket;

    public TCPServer(int port) throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                Capture capture = Capture.getCapture();
                Rectangle screenRectangle = capture.getScreenRectangle();
                int width = screenRectangle.width;
                ui=new ClientFrame(width, (int) (width * 0.618));
            } catch (AWTException e) {
                e.printStackTrace();
            }
        });
        serverSocket = new ServerSocket(port);
    }
    public void run() throws IOException {
        while (true){
            Socket accept = serverSocket.accept();
            InputStream inputStream = accept.getInputStream();
            BufferedImage image = ImageIO.read(inputStream);
            accept.close();
            ui.processImageEvent(image);
        }
    }
    public static void main(String[] args) throws IOException {
        Integer port = Integer.valueOf(args[0]);
        TCPServer tcpServer = new TCPServer(port);
        tcpServer.run();
    }
}
