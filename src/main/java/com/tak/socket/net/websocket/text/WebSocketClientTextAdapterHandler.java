package com.tak.socket.net.websocket.text;

import com.tak.socket.net.websocket.WebSocketClientInitializer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.net.URI;

public class WebSocketClientTextAdapterHandler extends WebSocketClientInitializer {

	ChannelHandler handler;

	protected WebSocketClientTextAdapterHandler(URI webSocketURL) {
		super(webSocketURL);
	}

	@Override
	protected void webSocketHandComplete(ChannelHandlerContext ctx) {
		ctx.channel().pipeline().addLast(handler);
		//为新加的handler手动触发必要事件
		ctx.fireChannelRegistered();
		ctx.fireChannelActive();
	}
}
