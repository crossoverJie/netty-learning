package com.crossoverjie.netty.server.learning.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Function: 将 int 转换为 String
 *
 * @author crossoverJie
 *         Date: 23/02/2018 12:37
 * @since JDK 1.8
 */
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer> {
    @Override
    protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        out.add(String.valueOf(msg)) ;
    }
}
