package com.crossoverjie.netty.client.learning.server;

import com.crossoverjie.netty.client.learning.handle.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 13/05/2018 23:45
 * @since JDK 1.8
 */
public class TimeClient {

    public static void main(String[] args) throws InterruptedException {
        String host = "127.0.0.1" ;
        int port = 11211 ;

        EventLoopGroup group = new NioEventLoopGroup() ;

        try {
            Bootstrap bootstrap = new Bootstrap() ;
            bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.SO_KEEPALIVE,true)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler()) ;
                }
            });

            // 启动客户端
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            // 等待连接关闭
            channelFuture.channel().closeFuture().sync() ;

        }finally {
            group.shutdownGracefully();
        }
    }
}
