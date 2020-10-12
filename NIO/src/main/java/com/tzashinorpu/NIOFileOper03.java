package com.tzashinorpu;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileOper03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel inputStreamChannel = fileInputStream.getChannel();


        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(2);

        while (true) {
            // 如果不对 buffer 不复位：第一次完整写入时 buffer position = 2，flip 后 position = 0 进行读取，第一次完整读取后 position = 2，
            // 此时进行再次读取时，由于 position = limit = 2，则无法对该 buffer 进行新数据的写入（read = 0：写入 0 个字节）
            byteBuffer.clear();
            int read = inputStreamChannel.read(byteBuffer);
            if (read == -1) {
                System.out.println("读取完成");
                break;
            }
            byteBuffer.flip();
            fileOutputStreamChannel.write(byteBuffer);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
