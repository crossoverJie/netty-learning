package com.crossoverjie.netty.server.learning.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 14/05/2018 10:11
 * @since JDK 1.8
 */
public class TimeDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4){
            return ;
        }
        out.add(in.readBytes(4)) ;
    }
}
