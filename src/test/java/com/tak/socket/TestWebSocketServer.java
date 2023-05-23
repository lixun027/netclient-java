/*
 * Created by 李迅 on 2023-05-15 17:00...
 */
package com.tak.socket;

import com.tak.socket.net.server.NettyServer;
import com.tak.socket.net.websocket.WebSocketServerInitializer;
import io.netty.channel.*;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 17:00
 */
public class TestWebSocketServer {

	public void test1() {
		NettyServer ns = new NettyServer("127.0.0.1", 7777, 0, 0, new ChannelInitializer<>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(new HttpServerCodec())
						.addLast(new ChunkedWriteHandler())
						.addLast(new HttpObjectAggregator(1024 * 1024 * 10))
						.addLast(new WebSocketServerProtocolHandler("/socket.io", null, false, 1024 * 1024 * 50, false, true, 10000L))
				;
			}
		});

		ns.start();
	}

	public void test2() {
		NettyServer ns = new NettyServer("127.0.0.1", 7777, 0, 0, new WebSocketServerInitializer("/socket.io") {
			@Override
			protected void webSocketHandComplete(ChannelHandlerContext ctx) {
				ChannelPipeline p = ctx.pipeline();
				p.addLast(new SimpleChannelInboundHandler<TextWebSocketFrame>() {

					// 服务端收到消息
					@Override
					protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
						System.err.println("收到消息 = " + msg.text());
						// 向客户端发送消息
						ctx.channel().writeAndFlush(new TextWebSocketFrame("我是回信"));
					}
				});

				//为新加的handler手动触发必要事件
				ctx.fireChannelRegistered();
				ctx.fireChannelActive();
			}
		});

		ns.start();
	}


	public static void main(String[] args) {
		TestWebSocketServer testServer = new TestWebSocketServer();
		testServer.test2();
	}

}
