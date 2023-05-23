/*
 * Created by 李迅 on 2023-05-17 19:08...
 */
package com.tak.socket.config.runner;

import com.tak.socket.net.client.NettyClient;
import com.tak.socket.net.tcp.TcpClientInitializer;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-17 19:08
 */
@Component
@Slf4j
public class StartTcpRunner extends AbstractApplicationRunner {

	@Override
	public void run(ApplicationArguments args) {
		readerWriter.setWrite();
		startTcpClient();
	}

	private void startTcpClient() {
		NettyClient nettyClient = new NettyClient(ipAddressConfig.getTcpHost(), Integer.parseInt(ipAddressConfig.getTcpPort()), new TcpClientInitializer() {
			@Override
			protected void tcpHandComplete(ChannelHandlerContext ctx) {

				readerWriter.write.write(ctx.channel());

				ctx.fireChannelRegistered();
				ctx.fireChannelActive();
			}
		});

		nettyClient.start();
	}

}
