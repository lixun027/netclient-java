/*
 * Created by 李迅 on 2023-05-16 20:26...
 */
package com.tak.socket.config.runner;

import com.tak.socket.net.client.NettyClient;
import com.tak.socket.net.websocket.WebSocketClientInitializer;
import com.tak.socket.trans.enums.C2NetTypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-16 20:26
 */
@Component
@Slf4j
public class StartWebSocketRunner extends AbstractApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		readerWriter.setReader();
		startWebSocketDroneIdClient();
		startWebSocketRadarClient();
		startWebSocketPostureClient();
	}

	private void startWebSocketDroneIdClient() throws Exception {
		final URI webSocketURL = new URI(ipAddressConfig.getWebsocketProto() + "://"  //
				+ ipAddressConfig.getWebsocketHost() + ":" //
				+ ipAddressConfig.getWebsocketPort() //
				+ ipAddressConfig.getWebsocketDroneIdUrl());
		startWebSocketClient(webSocketURL, C2NetTypeEnum.WS_DRONE_ID);
	}

	private void startWebSocketRadarClient() throws Exception {
		final URI webSocketURL = new URI(ipAddressConfig.getWebsocketProto() + "://"  //
				+ ipAddressConfig.getWebsocketHost() + ":" //
				+ ipAddressConfig.getWebsocketPort() //
				+ ipAddressConfig.getWebsocketRadarUrl());
		startWebSocketClient(webSocketURL, C2NetTypeEnum.WS_RADAR_AIRPLANE);
	}

	private void startWebSocketPostureClient() throws Exception {
		final URI webSocketURL = new URI(ipAddressConfig.getWebsocketProto() + "://"  //
				+ ipAddressConfig.getWebsocketHost() + ":" //
				+ ipAddressConfig.getWebsocketPort() //
				+ ipAddressConfig.getWebsocketPostureUrl());
		startWebSocketClient(webSocketURL, C2NetTypeEnum.WS_POSTURE);
	}

	private void startWebSocketClient(URI webSocketURL, C2NetTypeEnum type) {
		NettyClient nettyClient = new NettyClient(ipAddressConfig.getWebsocketHost(), Integer.parseInt(ipAddressConfig.getWebsocketPort()), new WebSocketClientInitializer(webSocketURL) {
			@Override
			protected void webSocketHandComplete(ChannelHandlerContext ctx) {
				ChannelPipeline p = ctx.pipeline();
				p.addLast(new WebSocketReadHandler(type));
				ctx.fireChannelRegistered();
				ctx.fireChannelActive();
			}
		});
		nettyClient.start();
	}

	@AllArgsConstructor
	private class WebSocketReadHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

		C2NetTypeEnum type;

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
			readerWriter.reade.read(msg.text(), type);
		}
	}

}
