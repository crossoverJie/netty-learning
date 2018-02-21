package com.crossoverjie.netty.client.learning.server;

import com.crossoverjie.netty.client.learning.handle.EchoClientHandle;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;

import java.net.InetSocketAddress;


/**
 * Function:客户端
 *
 * @author crossoverJie
 *         Date: 16/02/2018 18:09
 * @since JDK 1.8
 */
public class EchoClient {

    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EchoClient.class);

    private final static String HOST = "127.0.0.1" ;
    private final static int PORT = 11211 ;


    public static void main(String[] args) throws InterruptedException {
        new EchoClient().startClient();
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


    private void startClient() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup() ;

        try {
            Bootstrap bootstrap = new Bootstrap() ;
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                            LOGGER.info("msg={}", msg.toString(CharsetUtil.UTF_8));
                        }
                    });

            ChannelFuture future = bootstrap.connect("www.manning.com", 80).sync();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {


                    if (future.isSuccess()){
                        LOGGER.info("连接成功");
                    }else {
                        LOGGER.info("连接失败");
                        future.cause().printStackTrace();
                    }
                }
            });
        }finally {

            group.shutdownGracefully().sync() ;
        }

    }
}
