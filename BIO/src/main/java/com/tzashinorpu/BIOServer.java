package com.tzashinorpu;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(22222);
        System.out.println("服务器启动了");
        while (true) {
            System.out.println("线程 id=" + Thread.currentThread().getId() + ",线程名=" + Thread.currentThread().getName() + "在等待客户的连接...");
            final Socket socket = serverSocket.accept();
            System.out.println("有一个客户端连接进来...");
            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    private static void handler(Socket socket) {
        try {
            System.out.println("线程 id=" + Thread.currentThread().getId() + ",线程名=" + Thread.currentThread().getName() + "准备处理连入的客户端请求...");
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                System.out.println("线程 id=" + Thread.currentThread().getId() + ",线程名=" + Thread.currentThread().getName() + "读取连入的客户端的数据为：");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和 client 的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
