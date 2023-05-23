/*
 * Created by 李迅 on 2023-05-15 21:10...
 */
package com.tak.socket;

import com.tak.socket.net.client.NettyClient;
import com.tak.socket.net.websocket.WebSocketClientInitializer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 21:10
 */
public class TestWebSocketClient {

	public void test1() throws URISyntaxException {
//		final URI webSocketURL = new URI("ws://127.0.0.1:7777/socket.io");

		// drone id
		final URI webSocketURL1 = new URI("ws://127.0.0.1:9906/ws/v2/droneid/heart");

		// 雷达
		final URI webSocketURL2 = new URI("ws://127.0.0.1:9906/ws/radar/track");


		NettyClient nettyClient1 = new NettyClient("127.0.0.1", 9906, new WebSocketClientInitializer(webSocketURL1) {
			@Override
			protected void webSocketHandComplete(ChannelHandlerContext ctx) {
				ChannelPipeline p = ctx.pipeline();
				p.addLast(new SimpleChannelInboundHandler<TextWebSocketFrame>() {
					@Override
					protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
						System.err.println("DRONE 客户端收到消息 = " + msg.text());
					}
				});

				// 发送消息
				ctx.channel().writeAndFlush(new TextWebSocketFrame("aaaaaaaaaaaaaa"));

				//为新加的handler手动触发必要事件
				ctx.fireChannelRegistered();
				ctx.fireChannelActive();
			}
		});

		nettyClient1.start();



		NettyClient nettyClient2 = new NettyClient("127.0.0.1", 9906, new WebSocketClientInitializer(webSocketURL2) {
			@Override
			protected void webSocketHandComplete(ChannelHandlerContext ctx) {
				ChannelPipeline p = ctx.pipeline();
				p.addLast(new SimpleChannelInboundHandler<TextWebSocketFrame>() {
					@Override
					protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
						System.err.println("RADAR =====  客户端收到消息 = " + msg.text());
					}
				});

				// 发送消息
				ctx.channel().writeAndFlush(new TextWebSocketFrame("aaaaaaaaaaaaaa"));

				//为新加的handler手动触发必要事件
				ctx.fireChannelRegistered();
				ctx.fireChannelActive();
			}
		});

		nettyClient2.start();
	}

	public static void main(String[] args) throws URISyntaxException {
		TestWebSocketClient testClient = new TestWebSocketClient();
		testClient.test1();
	}

}
