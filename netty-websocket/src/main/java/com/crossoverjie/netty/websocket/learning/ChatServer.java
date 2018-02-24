package com.crossoverjie.netty.websocket.learning;

import com.crossoverjie.netty.websocket.learning.channel.initial.ChatServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.slf4j.Logger;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

/**
 * Function: 启动服务
 *
 * @author crossoverJie
 *         Date: 24/02/2018 22:22
 * @since JDK 1.8
 */
public class ChatServer {

    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ChatServer.class);

    private final static int PORT = 11211;

    private ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    private EventLoopGroup group = new NioEventLoopGroup();

    private Channel channel;

    private static SslContext sslContext ;

    public static void main(String[] args) throws CertificateException, SSLException {

        SelfSignedCertificate cert = new SelfSignedCertificate();
        sslContext = SslContext.newServerContext(
                cert.certificate(), cert.privateKey());

        ChatServer chatServer = new ChatServer();
        ChannelFuture future = chatServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                chatServer.destroy();
            }
        });

        future.channel().closeFuture().syncUninterruptibly() ;

    }

    private ChannelFuture start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChatServerInitializer(channelGroup,sslContext)) ;

        ChannelFuture future = bootstrap.bind(new InetSocketAddress(PORT));

        //同步
        future.syncUninterruptibly();
        channel = future.channel() ;
        return future ;
    }

    private void destroy(){
        LOGGER.debug("destroy.....");
        if (channel != null){
            channel.close() ;
        }
        channelGroup.close() ;
        group.shutdownGracefully() ;
    }
}
