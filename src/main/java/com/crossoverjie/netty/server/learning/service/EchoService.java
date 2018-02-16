package com.crossoverjie.netty.server.learning.service;

import com.crossoverjie.netty.server.learning.handle.EchoServiceHandle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Function:引导启动程序
 *
 * @author crossoverJie
 *         Date: 14/02/2018 01:04
 * @since JDK 1.8
 */
public class EchoService {

    private final static int PORT = 11211;

    public static void main(String[] args) throws InterruptedException {
        new EchoService().start();
    }

    private void start() throws InterruptedException {

        final EchoServiceHandle handle = new EchoServiceHandle() ;

        EventLoopGroup group = new NioEventLoopGroup() ;

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap() ;
            serverBootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(PORT))
                    .childHandler(new ChannelInitializer() {

                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(handle) ;
                        }
                    });

            ChannelFuture future = serverBootstrap.bind().sync() ;
            future.channel().closeFuture().sync() ;

        }finally {
            group.shutdownGracefully().sync();
        }
    }
}
