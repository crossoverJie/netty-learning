package com.crossoverjie.netty.client.learning.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Test;

public class EchoClientTest {

    @Test
    public void test() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8);
        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println((char) byteBuf.getByte(i));
        }
    }

}