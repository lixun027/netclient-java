/*
 * Created by 李迅 on 2023-05-15 19:36...
 */
package com.tak.socket.net.websocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 19:36
 */
@ChannelHandler.Sharable
@Slf4j
public abstract class WebSocketServerInitializer extends ChannelInitializer<Channel> implements ChannelInboundHandler {

	protected final String websocketPath;

	public WebSocketServerInitializer(String websocketPath) {
		this.websocketPath = websocketPath;
	}

	@Override
	protected final void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new ChunkedWriteHandler());
		pipeline.addLast(new HttpObjectAggregator(1024 * 1024 * 50));
		pipeline.addLast(new WebSocketServerProtocolHandler(websocketPath, null, false, 1024 * 1024 * 50, false, true, 10000L));
		pipeline.addLast(new WebSocketConnectedServerHandler());
	}

	private class WebSocketConnectedServerHandler extends ChannelInboundHandlerAdapter {

		@SuppressWarnings("deprecation")
		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
			if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {//旧版本
				webSocketHandComplete(ctx);
				ctx.pipeline().remove(this);
				return;
			}
			if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {//新版本
				webSocketHandComplete(ctx);
				ctx.pipeline().remove(this);
				return;
			}
			super.userEventTriggered(ctx, evt);
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
//			new NettyConnection(ctx);
			super.channelActive(ctx);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			super.channelRead(ctx, msg);
		}
	}

	protected abstract void webSocketHandComplete(ChannelHandlerContext ctx);
}
