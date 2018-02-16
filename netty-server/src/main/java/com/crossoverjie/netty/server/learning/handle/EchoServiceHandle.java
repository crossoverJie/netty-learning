package com.crossoverjie.netty.server.learning.handle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;

/**
 * Function:消息处理器
 *
 * @author crossoverJie
 *         Date: 14/02/2018 00:39
 * @since JDK 1.8
 */
@ChannelHandler.Sharable
public class EchoServiceHandle  extends ChannelInboundHandlerAdapter{
    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EchoServiceHandle.class);

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        LOGGER.info("msg={}", byteBuf.toString(CharsetUtil.UTF_8));
        //将收到的消息返回给客户端
        channelHandlerContext.write(byteBuf) ;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {

        //将未决消息冲刷到远程节点，并关闭连接
        channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {

        throwable.printStackTrace();
        channelHandlerContext.close();
    }
}
