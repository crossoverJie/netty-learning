package com.crossoverjie.netty.client.learning.server;

import com.crossoverjie.netty.client.learning.handle.EchoClientHandle;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 16/02/2018 18:09
 * @since JDK 1.8
 */
public class EchoClient {

    private final static String HOST = "127.0.0.1" ;
    private final static int PORT = 11211 ;


    public static void main(String[] args) throws InterruptedException {
        new EchoClient().start();
    }


    private void start() throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup() ;

        try {
            Bootstrap bootstrap = new Bootstrap() ;
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(HOST,PORT))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandle());
                        }
                    });

            //阻塞同步完成连接
            ChannelFuture future = bootstrap.connect().sync() ;
            future.channel().closeFuture().sync() ;

        }finally {
            //关闭线程池
            group.shutdownGracefully().sync() ;
        }
    }
}
