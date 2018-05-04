package com.fs.frame.client.capture;

public class CaptureFactory {
    private CaptureFactory(){}
    private static CaptureFactory factory = new CaptureFactory();
    public static CaptureFactory get(){
        return factory;
    }
    public Capture getCapture(Class<? extends Capture> clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
