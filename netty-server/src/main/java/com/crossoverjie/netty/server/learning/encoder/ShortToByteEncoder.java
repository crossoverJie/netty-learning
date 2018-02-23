package com.crossoverjie.netty.server.learning.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Function:编码器
 *
 * @author crossoverJie
 *         Date: 23/02/2018 15:37
 * @since JDK 1.8
 */
public class ShortToByteEncoder extends MessageToByteEncoder<Short> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Short msg, ByteBuf out) throws Exception {
        out.writeShort(msg) ;
    }
}
