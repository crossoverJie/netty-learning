package com.crossoverjie.netty.websocket.learning.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * Function: websocket handler
 *
 * @author crossoverJie
 *         Date: 24/02/2018 15:31
 * @since JDK 1.8
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    private ChannelGroup group  ;

    public TextWebSocketFrameHandler(ChannelGroup group){
        this.group = group ;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE){
            //删除之前 HttpRequestHandle
            ctx.pipeline().remove(HttpRequestHandle.class) ;

            //通知所有已经连接的客户端 有新的连接来了
            group.writeAndFlush(new TextWebSocketFrame("新的客户端=" + ctx.channel() + "连接上来了")) ;

            //将当前的 channel 也就是 websocket channel 加入到 channelGroup 当中
            group.add(ctx.channel()) ;
        }else {
            //交给下一个 channelHandler 处理
            super.userEventTriggered(ctx, evt);
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //写到 channelGroup 中所有的客户端  retain 引用次数+1  因为 channelRead0 方法返回后引用次数将减一
        group.writeAndFlush(msg.retain()) ;

    }
}
