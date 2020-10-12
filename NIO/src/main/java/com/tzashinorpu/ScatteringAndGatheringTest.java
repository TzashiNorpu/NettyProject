package com.tzashinorpu;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        System.out.println("服务端等待连接...");
        SocketChannel socketChannel = serverSocketChannel.accept();
        int msgLen = 8;
        System.out.println("新接入一个客户端");
        while (true) {
            int byteRead = 0;
            while (byteRead < msgLen) {
                System.out.println("等待客户端发送数据...");
                // 将数据从 channel 读取到 bytebuffers 中
                // 从一个客户端发送过来的数据会依次写入到 bytebuffers 数组的 bytebuffer 中
                // 发送的超过 bytebuffers 数组中 bytebuffer 的总长度的数据，channel 中也不会丢失，可以再次从 channel 中将数据读入到 buffer 中
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                int index = 0;
                System.out.println("byte read = " + byteRead);
                // 输出两行是因为有数组里有两个 byteBuffer
                Arrays.asList(byteBuffers).stream().map(buffer -> "position=" + buffer.position() + ",limit=" +
                        buffer.limit()).forEach(System.out::println);
            }

            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());
            long byteWrite = 0;
            while (byteWrite < msgLen) {
                System.out.println("服务端准备发送数据...");
                // 将数据从 bytebuffers 写入到 channel 中
                // buffers 中的数据都可以发送到和客户端连接的 channel 中
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }

            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());
            System.out.println("byteRead=" + byteRead + ",byteWrite=" + byteWrite + ",msgLen=" + msgLen);
        }
    }
}
