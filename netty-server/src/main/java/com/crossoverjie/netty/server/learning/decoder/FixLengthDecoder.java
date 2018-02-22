package com.crossoverjie.netty.server.learning.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Function: 解码器
 *
 * @author crossoverJie
 *         Date: 22/02/2018 23:53
 * @since JDK 1.8
 */
public class FixLengthDecoder extends ByteToMessageDecoder {


    private int length ;

    public FixLengthDecoder(int length) {
        if (length <= 0){
            throw new IllegalArgumentException("截取长度不能小于0") ;
        }
        this.length = length;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        while (in.readableBytes() >= length){
            ByteBuf byt = in.readBytes(length) ;
            out.add(byt) ;
        }

    }
}
