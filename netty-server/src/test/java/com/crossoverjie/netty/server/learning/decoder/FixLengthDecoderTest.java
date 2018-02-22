package com.crossoverjie.netty.server.learning.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

public class FixLengthDecoderTest {


    @Test
    public void decode() throws Exception {

        ByteBuf buf = Unpooled.buffer();

        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf duplicate = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixLengthDecoder(3));
        channel.writeInbound(buf.retain());
        channel.finish();

        ByteBuf read = channel.readInbound();
        Assert.assertEquals(duplicate.readSlice(3), read);


        read = channel.readInbound();
        Assert.assertEquals(duplicate.readSlice(3), read);

        read = channel.readInbound();
        Assert.assertEquals(duplicate.readSlice(3), read);

        read.release();


    }
    @Test
    public void decode2() throws Exception {

        ByteBuf buf = Unpooled.buffer();

        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf duplicate = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixLengthDecoder(3));
        Assert.assertFalse(channel.writeInbound(buf.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(buf.readBytes(7)));


        channel.finish();

        ByteBuf read = channel.readInbound();
        Assert.assertEquals(duplicate.readSlice(3), read);


        read = channel.readInbound();
        Assert.assertEquals(duplicate.readSlice(3), read);

        read = channel.readInbound();
        Assert.assertEquals(duplicate.readSlice(3), read);

        read.release();


    }

}