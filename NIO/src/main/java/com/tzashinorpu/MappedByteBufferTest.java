package com.tzashinorpu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("r.txt", "rw");
        FileChannel randomAccessFileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = randomAccessFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) 'L');
        randomAccessFile.close();
    }
}
