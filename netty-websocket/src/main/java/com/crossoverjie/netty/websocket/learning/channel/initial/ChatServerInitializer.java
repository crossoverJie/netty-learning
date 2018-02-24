package com.crossoverjie.netty.websocket.learning.channel.initial;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 24/02/2018 19:52
 * @since JDK 1.8
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {

    private ChannelGroup group ;

    public ChatServerInitializer(ChannelGroup group){
        this.group = group ;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //http 服务器请求的 编码器和解码器的聚合
        pipeline.addLast(new HttpServerCodec()) ;
    }
}
