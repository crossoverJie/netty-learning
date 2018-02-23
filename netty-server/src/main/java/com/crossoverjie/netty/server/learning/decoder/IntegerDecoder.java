package com.crossoverjie.netty.server.learning.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Function: int 解码器
 *
 * @author crossoverJie
 *         Date: 23/02/2018 12:26
 * @since JDK 1.8
 */
public class IntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() >= 4) {
            out.add(in.readInt());
        }
    }
}
