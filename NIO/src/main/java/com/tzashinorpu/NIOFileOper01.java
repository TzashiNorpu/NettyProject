package com.tzashinorpu;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileOper01 {
    public static void main(String[] args) throws Exception {

        String str = "hello,尚硅谷~";
        FileOutputStream fos = new FileOutputStream("d:\\file01.txt");
        //使用FileChannel 通道
        //fos.getChannle 返回的是 FileChanel 的实现子类 FileChannelImpl
        //可以追下源代码
        FileChannel fc = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
		/*
		 *NIO 中的通道是从输出流对象里通过 getChannel 方法获取到的，该通道是双向的，既可
以读，又可以写。在往通道里写数据之前，必须通过 put 方法把数据存到 ByteBuffer 中，然
后通过通道的 write 方法写数据。在 write 之前，需要调用 flip 方法翻转缓冲区，把内部重置
到初始位置，这样在接下来写数据时才能把所有数据写到通道里
		 */
        buffer.put(str.getBytes());
        buffer.flip();
        // channel 从 OutputStream 获取，此时数据不在 channel中，将 buffer 的数据写入到 buffer中
        fc.write(buffer);
        fos.close();
    }
}
