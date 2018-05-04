import com.fs.frame.client.capture.Capture;
import com.fs.frame.client.capture.CaptureFactory;
import com.fs.frame.client.capture.RobotCapture;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;
import org.junit.After;
import org.junit.Before;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


public class Test {
    long start;
    Capture capture;

    @Before
    public void setUp() {
        capture = CaptureFactory.get().getCapture(RobotCapture.class);
        start = System.currentTimeMillis();
    }

    @After
    public void tearDown() throws Exception {
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @org.junit.Test
    public void testCapture() throws FrameGrabber.Exception {
        for (int i = 0; i < 100; i++) {
            BufferedImage image = capture.captureScreen2Image();
        }
    }



    @org.junit.Test
    public void testGrabByopencv() throws IOException, ClassNotFoundException {
        Java2DFrameConverter converter = new Java2DFrameConverter();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


        int x = 0, y = 0, w = 1024, h = 768;
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(":0.0+" + x + "," + y);
        grabber.setFormat("x11grab");
        grabber.setImageWidth(screenSize.width);
        grabber.setImageHeight(screenSize.height);
        grabber.setImageMode(FrameGrabber.ImageMode.COLOR);
//        grabber.grabImage();
        grabber.start();


        CanvasFrame frame = new CanvasFrame("Screen RobotCapture");


//        grabber.stop();
        while (frame.isVisible()) {
            long start = System.currentTimeMillis();
//            TcpPacket tcpPacket = cvCapture.captureScreen();
            Frame grab = grabber.grabImage();
//            MyFrame myFrame = new MyFrame(new MyFrame(grab).toBytes());
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//            objectOutputStream.writeObject(myFrame);
//            System.out.println(outputStream.toByteArray().length);

//            packetFrame.image=new ByteBuffer[]{tcpPacket.buffer};
//            BufferedImage bufferedImage = converter.getBufferedImage(grab);
            frame.showImage(grab);
            long end = System.currentTimeMillis() - start;
            System.out.println(end);
        }
        frame.dispose();
        grabber.stop();
    }
}
