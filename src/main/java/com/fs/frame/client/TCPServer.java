package com.fs.frame.client;


import com.fs.frame.beans.ImageFrame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public class TCPServer {
    public ClientFrame ui;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Java2DFrameConverter converter = new Java2DFrameConverter();
    int port;
    public TCPServer() {
    }
    public TCPServer init(int port,int width,int height) throws IOException {
        this.port=port;
        SwingUtilities.invokeLater(() -> {
            ui=new ClientFrame("Capture Screen",width, height);
        });
        return this;
    }
    public TCPServer init(int port) throws IOException {
        init(port,screenSize.width,screenSize.height);
        return this;
    }

    public void newStart() throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(port));
        ImageFrame imageFrame = new ImageFrame();
        SocketChannel accept = channel.accept();
        while (true){
            imageFrame.readData(accept);
            BufferedImage image = converter.convert(imageFrame);
            ui.processImageEvent(image);
            imageFrame.clear();
            System.out.println("process one");
        }

    }
    public static void main(String[] args) throws IOException {
        TCPServer tcpServer = new TCPServer().init(8888);
        tcpServer.newStart();
    }
}
