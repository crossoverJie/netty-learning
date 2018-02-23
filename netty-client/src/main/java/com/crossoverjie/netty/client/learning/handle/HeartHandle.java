package com.crossoverjie.netty.client.learning.handle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 23/02/2018 22:50
 * @since JDK 1.8
 */
public class HeartHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        ByteBuf byteBuf = Unpooled.copiedBuffer("heart", CharsetUtil.UTF_8);

        if (evt instanceof IdleStateEvent){
            //如果是超时事件
            ctx.writeAndFlush(byteBuf)
            .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }

        super.userEventTriggered(ctx, evt);
    }
}
