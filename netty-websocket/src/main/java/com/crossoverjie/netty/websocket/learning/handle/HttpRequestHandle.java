package com.crossoverjie.netty.websocket.learning.handle;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 24/02/2018 10:15
 * @since JDK 1.8
 */
public class HttpRequestHandle extends SimpleChannelInboundHandler<FullHttpRequest> {


    private String wsUri;

    public HttpRequestHandle(String wsUri) {
        this.wsUri = wsUri;
    }

    /**
     * index.html
     */
    private static File INDEX;


    static {
        //初始化 index.html
        URL location = HttpRequestHandle.class.getProtectionDomain()
                .getCodeSource().getLocation();

        try {
            String path = location.toURI() + "index.html";
            path = !path.contains("file:") ? path : path.substring(5);
            INDEX = new File(path);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(
                    "Unable to locate index.html", e);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        if (wsUri.equalsIgnoreCase(request.getUri())) {
            //如果是 websocket 请求则交给下一个 channelHandler 处理
            ctx.fireChannelRead(request.retain());
        } else {

            //读取 index.html
            RandomAccessFile file = new RandomAccessFile(INDEX, "r");
            DefaultHttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
            response.headers().set("Content-Type", "text/html; charset=UTF-8");

            //是 keepAlive 则添加对应的头信息
            boolean keepAlive = HttpHeaders.isKeepAlive(request);
            if (keepAlive) {
                response.headers().set("Content-Length", file.length());
                response.headers().set("Connection", "keep-alive");
            }
            //将 Response 写回客户端
            ctx.write(response);

            //将 index.html 写回客户端
            if (ctx.pipeline().get(SslHandler.class) == null) {
                //没有加密
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            }else {
                ctx.write(new ChunkedNioFile(file.getChannel())) ;
            }

            //写 LastHttpContent 写回客户端
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

            if (!keepAlive){
                //没有 keepAlive 就断开连接
                future.addListener(ChannelFutureListener.CLOSE) ;
            }
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
