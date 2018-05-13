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
public class TimeServiceHandle extends ChannelInboundHandlerAdapter{
    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TimeServiceHandle.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L)) ;
        ChannelFuture channelFuture = ctx.writeAndFlush(time);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {

                if (channelFuture == future){
                    ctx.close() ;
                }
            }
        }) ;
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
