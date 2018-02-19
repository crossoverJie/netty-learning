package com.crossoverjie.netty.client.learning.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import org.junit.Test;
import org.slf4j.Logger;

public class EchoClientTest {

    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EchoClientTest.class);

    @Test
    public void test() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8);
        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println((char) byteBuf.getByte(i));
        }

        System.out.println("===============");

        System.out.println(byteBuf.readerIndex(0).toString(CharsetUtil.UTF_8));
    }

    /**
     * 读取所有的可读字节
     */
    @Test
    public void readByteTest() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8);
        while (byteBuf.isReadable()) {
            System.out.println((char) byteBuf.readByte());
        }
    }

    /**
     * 写入可用字节
     */
    @Test
    public void writeByteTest() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("", CharsetUtil.UTF_8);
        System.out.println(byteBuf.writableBytes());
        byteBuf.writeInt(1);
        System.out.println(byteBuf.writableBytes());


        for (int i = 0; i <= 15; i++) {
            byteBuf.writeInt(1);
        }

        System.out.println(byteBuf.writableBytes());
    }

    /**
     * 查找换行符
     */
    @Test
    public void findByteTest() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("abc\r", CharsetUtil.UTF_8);
        int index = byteBuf.forEachByte(ByteProcessor.FIND_CR);
        System.out.println(index);
    }

    /**
     * set 方法
     */
    @Test
    public void setByteTest() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("netty", CharsetUtil.UTF_8);

        System.out.println((char) byteBuf.getByte(0));

        int readerIndex = byteBuf.readerIndex();
        LOGGER.info("readerIndex={}", readerIndex);

        int writerIndex = byteBuf.writerIndex();
        LOGGER.info("writerIndex={}", writerIndex);

        byteBuf.setByte(0, (byte) 'b');

        System.out.println((char) byteBuf.getByte(0));

        readerIndex = byteBuf.readerIndex();
        LOGGER.info("readerIndex={}", readerIndex);

        writerIndex = byteBuf.writerIndex();
        LOGGER.info("writerIndex={}", writerIndex);

    }

}