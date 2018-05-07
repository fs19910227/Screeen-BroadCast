package someTest;

import com.fs.frame.beans.ImageFrame;
import com.fs.frame.client.capture.Capture;
import com.fs.frame.client.capture.CaptureFactory;
import com.fs.frame.client.capture.RobotCapture;
import com.fs.frame.common.utills.CompressUtils;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;
import org.junit.After;
import org.junit.Before;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;


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
    public void testGrabByopencv() throws IOException, ClassNotFoundException, DataFormatException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //ipl convter
        OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();//转换器
        //java convter
        Java2DFrameConverter converter = new Java2DFrameConverter();
        OpenCVFrameConverter.ToMat matConverter = new OpenCVFrameConverter.ToMat();


//        int x = 0, y = 0, w = 1024, h = 768;
        int x = 0, y = 0, w = screenSize.width, h = screenSize.height;
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(":0.0+" + x + "," + y);
        grabber.setFormat("x11grab");
        grabber.setImageWidth(w);
        grabber.setImageHeight(h);
//        grabber.set
        grabber.setImageMode(FrameGrabber.ImageMode.COLOR);
        grabber.start();

        CanvasFrame frame = new CanvasFrame("Screen RobotCapture");


//        grabber.stop();
        while (frame.isVisible()) {
            start = System.currentTimeMillis();
//            TcpPacket tcpPacket = cvCapture.captureScreen();
            Frame grab = grabber.grabImage();

            ByteBuffer imageBuffer = (ByteBuffer) grab.image[0];

            ByteBuffer byteBuffer = CompressUtils.comressByteBuffer(imageBuffer);

            ByteBuffer deComressByteBuffer = CompressUtils.deComressByteBuffer(byteBuffer, imageBuffer.capacity());

//            ImageFrame image = new ImageFrame(grab);
//            image.setImage(deComressByteBuffer);
            frame.showImage(grab);
            imageBuffer.clear();
        }
        frame.dispose();
        grabber.stop();
    }
}
