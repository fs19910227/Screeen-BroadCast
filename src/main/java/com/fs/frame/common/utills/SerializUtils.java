package com.fs.frame.common.utills;

import java.io.*;

public final class SerializUtils<T> {
    public static byte[] Serializ(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(out);
        outputStream.writeObject(obj);
        return out.toByteArray();
    }
    public static Object unSerializ(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        Object o = objectInputStream.readObject();
        return o;
    }


}
