package com.tzashinorpu;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileOper02 {
    public static void main(String[] args) throws Exception {
        File file = new File("d:\\file01.txt");
        FileInputStream fis = new FileInputStream(file);
        //说明:
		/*
		 * 从输入流中获得一个通道，然后提供 ByteBuffer 缓冲区，该缓冲区的初始容量
		和文件的大小一样，最后通过通道的 read 方法把数据读取出来并存储到了 ByteBuffer 中
		 */
        FileChannel fc = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        //该通道可以写操作，也可以读操作

        // InputStream/OutputStream:输入/输出流，从内存角度出发，将硬盘数据读入到内存(输入流)，将内存数据写入到硬盘(输出流)
        // channel 从 InputStream 获取，此时数据在 channel中，从 channel 读出数据到 buffer中
        // channel 上的 read/write 在 channel 的角度：数据在 channel 中则是 read 到 buffer 中，数据在 buffer 中则是 write 到 channel 中
        fc.read(buffer);
        System.out.print(new String(buffer.array()));
        fis.close();
    }
}
