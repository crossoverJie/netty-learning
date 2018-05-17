package com.crossoverjie.netty.client.learning.server;

import com.crossoverjie.netty.client.learning.channel.init.CustomerHandleInitializer;
import com.crossoverjie.netty.client.learning.pojo.CustomProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 17/05/2018 19:01
 * @since JDK 1.8
 */
public class CustomerClient {

    public static void main(String[] args) {
        String host = "127.0.0.1" ;
        int port = 11211 ;


        EventLoopGroup group = new NioEventLoopGroup() ;
        try {

            Bootstrap bootstrap = new Bootstrap() ;
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new CustomerHandleInitializer())
                    ;

            ChannelFuture future = bootstrap.connect(host, port).sync();

            CustomProtocol customProtocol = new CustomProtocol() ;
            customProtocol.setHeader(99999);
            customProtocol.setContent("你好 netty");

            future.channel().writeAndFlush(customProtocol) ;

            future.channel().closeFuture().sync() ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            group.shutdownGracefully();
        }
    }
}
