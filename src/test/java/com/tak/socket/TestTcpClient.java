/*
 * Created by 李迅 on 2023-05-16 11:13...
 */
package com.tak.socket;

import com.tak.socket.net.client.NettyClient;
import com.tak.socket.net.tcp.TcpClientInitializer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-16 11:13
 */
public class TestTcpClient {

	public void test1() {

		NettyClient nc = new NettyClient("127.0.0.1", 7777, new TcpClientInitializer() {
			@Override
			protected void tcpHandComplete(ChannelHandlerContext ctx) {
				ChannelPipeline pipeline = ctx.pipeline();

				pipeline.addLast(new StringEncoder());
				pipeline.addLast(new SimpleChannelInboundHandler<TextWebSocketFrame>() {
					@Override
					protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

					}
				});
				ctx.channel().writeAndFlush("tcp");

				//为新加的handler手动触发必要事件
				ctx.fireChannelRegistered();
				ctx.fireChannelActive();
			}
		});

		nc.start();

	}

	public static void main(String[] args) {
		TestTcpClient testTcpClient = new TestTcpClient();
		testTcpClient.test1();
	}

}
