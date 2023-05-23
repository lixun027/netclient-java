/*
 * Created by 李迅 on 2023-05-15 16:40...
 */
package com.tak.socket.net.server;

import com.tak.socket.net.NetServer;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.ServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 16:40
 */
@Slf4j
public abstract class AbstractNettyServer implements NetServer {
	protected final InetSocketAddress local;
	private String name = "NettyServer";
	protected ServerSocketChannel serverChannel;

	public AbstractNettyServer(InetSocketAddress local) {
		this.local = local;
	}

	protected final boolean bind(InetSocketAddress local) {
		boolean isBind;
		try {
			ChannelFuture cf = doBind(local);
			isBind = cf.channel() != null && cf.channel().isActive();
			if (isBind) {
				serverChannel = (ServerSocketChannel) cf.channel();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}


		return isBind;
	}

	protected abstract ChannelFuture doBind(InetSocketAddress local);


	@Override
	public boolean start() {
		return bind(local);
	}

	@Override
	public void stop() {
		if (serverChannel != null) {
			serverChannel.close();
		}
	}


	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final void setName(String name) {
		if (name != null) {
			this.name = name;
		}
	}

	@Override
	public boolean isActive() {
		return serverChannel != null && serverChannel.isActive();
	}
}
