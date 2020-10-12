package com.tzashinorpu;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NIOFileCopy {
    public static void main(String[] args) throws Exception {

        FileInputStream fis=new FileInputStream("1.png");
        FileOutputStream fos=new FileOutputStream("2.png");
        /*
         * 说明
         * 从两个流中得到两个通道，sourCh ,destCh ，
         * 然后直接调用 transferFrom 完成文件复制
         */
        FileChannel sourCh = fis.getChannel();
        FileChannel destCh = fos.getChannel();

        /*
         * transferFrom 方法可以将两个通道连接起来，进行数据传输
         */

        destCh.transferFrom(sourCh, 0, sourCh.size());
        sourCh.close();
        destCh.close();
        fis.close();
        fos.close();
        System.out.println("图片拷贝完毕~~");
    }
}
