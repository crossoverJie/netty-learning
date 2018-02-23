package com.crossoverjie.netty.server.learning.channel.init;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 23/02/2018 17:35
 * @since JDK 1.8
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast("decoder", new HttpRequestDecoder())
                .addLast("encoder", new HttpResponseEncoder())
        ;
    }
}
