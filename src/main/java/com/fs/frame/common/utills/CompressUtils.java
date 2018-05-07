package com.fs.frame.common.utills;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class CompressUtils {
    private static Deflater compressor = new Deflater();


    static {
        compressor.setLevel(Deflater.BEST_SPEED);
    }

    public static  ByteBuffer  comressByteBuffer(ByteBuffer buffer) throws IOException {
        byte[] bytes = new byte[buffer.capacity()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer.get();
        }
        buffer.flip();
        compressor.setInput(bytes);
        compressor.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(buffer.capacity());

        // Compress the data
        byte[] buf = new byte[1024 * 1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        bos.close();
        compressor.reset();
        return ByteBuffer.wrap(bos.toByteArray());
    }

    public static ByteBuffer deComressByteBuffer(ByteBuffer buffer, int capicity) throws DataFormatException {
        Inflater decompresser = new Inflater();
        // Encode a String into bytes
        byte[] input = buffer.array();
        byte[] result = new byte[capicity];

        // Decompress the bytes
        decompresser.setInput(input, 0, input.length);
        decompresser.inflate(result);
        decompresser.end();

        // Decode the bytes into a String
        return ByteBuffer.wrap(result);
    }

}
