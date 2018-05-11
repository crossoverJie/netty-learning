package com.crossoverjie.netty.server.learning.service;

import com.crossoverjie.netty.server.learning.channel.init.HttpPipelineInitializer;
import com.crossoverjie.netty.server.learning.handle.EchoServiceHandle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;

import java.net.InetSocketAddress;

/**
 * Function:引导启动程序
 *
 * @author crossoverJie
 *         Date: 14/02/2018 01:04
 * @since JDK 1.8
 */
public class EchoService {

    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EchoService.class);


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
                    //.childHandler(new HttpPipelineInitializer()) ;

            ChannelFuture future = serverBootstrap.bind().sync() ;
            future.channel().closeFuture().sync() ;

        }finally {
            group.shutdownGracefully().sync();
        }
    }


    private void startServer(){
        EventLoopGroup group = new NioEventLoopGroup() ;

        ServerBootstrap bootstrap = new ServerBootstrap() ;
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        LOGGER.debug("收到消息={}",msg.toString(CharsetUtil.UTF_8));
                    }
                });

        ChannelFuture future = bootstrap.bind(new InetSocketAddress(PORT));
        future.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()){
                LOGGER.debug("绑定端口成功");
            }else {
                LOGGER.debug("绑定端口失败");
                channelFuture.cause().printStackTrace() ;
            }
        });
    }
}
