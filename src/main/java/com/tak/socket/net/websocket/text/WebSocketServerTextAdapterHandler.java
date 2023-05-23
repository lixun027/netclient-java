package com.tak.socket.net.websocket.text;

import com.tak.socket.net.websocket.WebSocketServerInitializer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

public class WebSocketServerTextAdapterHandler extends WebSocketServerInitializer {

	ChannelHandler handler;

	public WebSocketServerTextAdapterHandler(String websocketPath) {
		super(websocketPath);
	}

	@Override
	protected void webSocketHandComplete(ChannelHandlerContext ctx) {
		ctx.channel().pipeline().addLast(handler);
		//为新加的handler手动触发必要事件
		ctx.fireChannelRegistered();
		ctx.fireChannelActive();
	}
}