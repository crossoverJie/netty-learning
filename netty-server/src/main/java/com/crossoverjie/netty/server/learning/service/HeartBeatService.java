package com.crossoverjie.netty.server.learning.service;

import com.crossoverjie.netty.server.learning.channel.init.CustomInitializer;
import com.crossoverjie.netty.server.learning.decoder.CustomDecoder;
import com.crossoverjie.netty.server.learning.handle.HeartBeatSimpleHandle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Function: 心跳长连接
 *
 * @author crossoverJie
 *         Date: 17/05/2018 17:51
 * @since JDK 1.8
 */
public class HeartBeatService {

    private final static Logger LOGGER = LoggerFactory.getLogger(HeartBeatService.class);

    private final static int PORT = 11211;

    public static void main(String[] args) {


        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(PORT))
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new IdleStateHandler(5, 0, 0))
                                    .addLast(new CustomDecoder())
                                    .addLast(new HeartBeatSimpleHandle())
                            ;
                        }
                    });

            ChannelFuture future = bootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
