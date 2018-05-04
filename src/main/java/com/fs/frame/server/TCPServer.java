package com.fs.frame.server;


import com.fs.frame.beans.ImageFrame;
import com.fs.frame.common.CommonServer;
import com.fs.frame.server.ui.ServerUI;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.channels.SocketChannel;


public class TCPServer extends CommonServer {
    public ServerUI ui;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Java2DFrameConverter converter = new Java2DFrameConverter();
    ImageFrame imageFrame = new ImageFrame();


    @Override
    protected void read(SocketChannel channel) throws IOException {
        try {
            imageFrame.readData(channel);
            System.out.println("read one "+imageFrame);
            BufferedImage image = converter.convert(imageFrame);
            ui.processImageEvent(image);
            System.out.println("process one");
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("client disconnect...");
            channel.close();
        } finally {
            imageFrame.clear();
        }
    }

    public TCPServer(int port) {
        super(port);
    }

    public TCPServer init(int width, int height) throws IOException {
        super.init();
        SwingUtilities.invokeLater(() -> {
            ui = new ServerUI("Capture Screen", width, height);
        });
        return this;
    }

    @Override
    public TCPServer init() throws IOException {
        this.init(screenSize.width, screenSize.height);
        return this;
    }


    public static void main(String[] args) throws IOException {
        TCPServer tcpServer = new TCPServer(8888).init(800,600);
        tcpServer.start();
    }
}
