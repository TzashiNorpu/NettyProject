package com.tzashinorpu.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

//服务器端的业务处理类
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取数据事件
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        // 如果当前的作业为较耗时作业，而 Workgroup 的线程有限：假设只有两个线程，当前这个作业耗时 20 s
        // 进入两个连接后，Workgroup 会开启两个线程进行处理（线程池内线程耗尽），此时若还有新连接进入，则 Workgroup 无法再次分配新进程进行处理
        // 新进入的连接只能等待 之前的连接处理完后进入当前的处理作业
        // 因此可以将这个耗时的作业进行异步处理
        Long l1 = System.currentTimeMillis();
        System.out.println("Server thread "+Thread.currentThread().getName()+ " get request at " + l1.toString());
        Thread.sleep(20*1000);
        Long l2 = System.currentTimeMillis();
        ctx.writeAndFlush(Unpooled.copiedBuffer(l2.toString(), CharsetUtil.UTF_8));
        System.out.println("go on...");
        /*
        System.out.println("服务器读取线程为："+Thread.currentThread().getName());
//        System.out.println("Server:" + ctx);
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发来的消息：" + buf.toString(CharsetUtil.UTF_8));
        Channel channel = ctx.channel();
        System.out.println("发送消息的客户端地址为："+channel.remoteAddress());*/
    }

    //数据读取完毕事件
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer(" hello client(>^ω^<)喵", CharsetUtil.UTF_8));
    }

    //异常发生事件
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) {
        ctx.close();
    }
}