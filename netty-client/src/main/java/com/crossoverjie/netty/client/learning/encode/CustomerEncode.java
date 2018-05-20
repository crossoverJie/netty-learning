package com.crossoverjie.netty.client.learning.encode;

import com.crossoverjie.netty.client.learning.pojo.CustomProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Function:编码
 *
 * @author crossoverJie
 *         Date: 17/05/2018 19:07
 * @since JDK 1.8
 */
public class CustomerEncode extends MessageToByteEncoder<CustomProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) throws Exception {

        out.writeLong(msg.getHeader()) ;
        out.writeBytes(msg.getContent().getBytes()) ;

    }
}
