/*
 * Created by 李迅 on 2023-05-16 10:47...
 */
package com.tak.socket.net.tcp;

import io.netty.channel.*;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-16 10:47
 */
public abstract class TcpClientInitializer extends ChannelInitializer<Channel> implements ChannelInboundHandler {

	@Override
	protected void initChannel(Channel ch) {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new StringEncoder());
		pipeline.addLast(new TcpConnectedClientHandler());
	}

	private class TcpConnectedClientHandler extends ChannelInboundHandlerAdapter {
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			super.channelActive(ctx);
			tcpHandComplete(ctx);
			ctx.pipeline().remove(this);
		}
	}

	protected abstract void tcpHandComplete(ChannelHandlerContext ctx);
}
