/*
 * Created by 李迅 on 2023-05-15 21:37...
 */
package com.tak.socket.net.websocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 21:37
 */
@ChannelHandler.Sharable
@Slf4j
public abstract class WebSocketClientInitializer extends ChannelInitializer<Channel> implements ChannelInboundHandler {
	protected final URI webSocketURL;

	protected WebSocketClientInitializer(URI webSocketURL) {
		this.webSocketURL = webSocketURL;
	}

	@Override
	protected void initChannel(Channel ch) {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast(new HttpClientCodec());
		pipeline.addLast(new ChunkedWriteHandler());
		pipeline.addLast(new HttpObjectAggregator(1024 * 1024 * 50));

		pipeline.addLast(new WebSocketClientProtocolHandler(WebSocketClientHandshakerFactory.newHandshaker(webSocketURL, WebSocketVersion.V13, null, false, new DefaultHttpHeaders())));
		pipeline.addLast(new WebSocketConnectedClientHandler());
	}

	private class WebSocketConnectedClientHandler extends ChannelInboundHandlerAdapter {
		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

			if (evt == WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE) {
				webSocketHandComplete(ctx);
				ctx.pipeline().remove(this);
			} else {
				super.userEventTriggered(ctx, evt);
			}
		}
	}

	protected abstract void webSocketHandComplete(ChannelHandlerContext ctx);
}
