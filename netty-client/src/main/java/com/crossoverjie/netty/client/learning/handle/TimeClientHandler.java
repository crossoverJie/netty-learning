package com.crossoverjie.netty.client.learning.handle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import sun.misc.resources.Messages_sv;

import java.util.Date;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 13/05/2018 23:53
 * @since JDK 1.8
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg ;
        try {
            long currentTimeMillis = (byteBuf.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println("=========="+new Date(currentTimeMillis));
            ctx.close();

        }finally {
            byteBuf.release() ;
        }

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
