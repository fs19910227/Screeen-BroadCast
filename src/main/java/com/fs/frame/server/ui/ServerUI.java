package com.fs.frame.server.ui;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * client receive remote screen image message
 *
 */
public class ServerUI extends JFrame implements ImageOberser {
    MyPanel panel= new MyPanel();


    @Override
    public void processImageEvent (BufferedImage image) {
        panel.image=image;
        panel.repaint();
    }


    class MyPanel extends JPanel{
        private BufferedImage image;
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Rectangle bounds = this.getBounds();
            g.clearRect(0,0,bounds.width,bounds.height);
            setBackground(Color.BLACK);  // Set background color for this JPanel

            g.drawImage(image,0,0,bounds.width,bounds.height,null);
        }

    }

    public ServerUI(String title, int width, int height) throws HeadlessException {
        super(title);

        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setResizable(true);       //设置窗体是否可以调整大小，参数为布尔值
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setName("Demo");
        this.add(panel);
        this.setVisible(true);
    }


}

