package com.crossoverjie.netty.server.learning.channel.init;

import com.crossoverjie.netty.server.learning.decoder.CustomDecoder;
import com.crossoverjie.netty.server.learning.handle.CustomSimpleHandle;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 17/05/2018 18:51
 * @since JDK 1.8
 */
public class CustomInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast(new CustomDecoder())
                .addLast(new CustomSimpleHandle());
    }
}
