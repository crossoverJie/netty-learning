package com.crossoverjie.netty.server.learning.handle;

import com.crossoverjie.netty.server.learning.pojo.CustomProtocol;
import com.crossoverjie.netty.server.learning.service.CustomService;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 17/05/2018 18:52
 * @since JDK 1.8
 */
public class CustomSimpleHandle extends SimpleChannelInboundHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomSimpleHandle.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        CustomProtocol customProtocol = (CustomProtocol) msg;
        LOGGER.info("customProtocol={}", customProtocol);
        ctx.writeAndFlush(Unpooled.copiedBuffer(customProtocol.toString(), CharsetUtil.UTF_8)).addListener(ChannelFutureListener.CLOSE);
    }
}
