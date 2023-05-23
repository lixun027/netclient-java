/*
 * Created by 李迅 on 2023-05-15 20:48...
 */
package com.tak.socket.net.client;

import com.tak.socket.net.NetClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 20:48
 */
@Slf4j
public abstract class AbstractNettyClient implements NetClient {

	private String name = "";
	protected volatile boolean reconnect;
	protected long timeMills;
	protected final InetSocketAddress address;
	private Channel channel;

	/**
	 * 重连调度器
	 */
//	private ScheduledExecutorService reconnectExecutor;
	public AbstractNettyClient(InetSocketAddress address) {
		this.address = address;
	}

	protected abstract EventLoopGroup getIoWorkers();

//	protected final ScheduledExecutorService getReconnectExecutor() {
//		if (reconnectExecutor != null) {
//			return reconnectExecutor;
//		}
//		return getIoWorkers();
//	}

	public final Channel getChannel() {
		return channel;
	}

	protected final void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public boolean isActive() {
		return channel != null && channel.isActive();
	}


	@Override
	public final void start() {
		if (isActive()) {
			stop();
		}
		connect(address, false);
	}

	@Override
	public final void stop() {
		if (channel != null && channel.isActive()) {
			disableReconnect();
			channel.close();
		}
	}

	@Override
	public void disableReconnect() {
		this.reconnect = false;
	}

	protected final boolean connect(InetSocketAddress target, boolean isReConnect) {
		boolean isConnect = false;
		try {
			long startConnectTime = System.currentTimeMillis();
			log.info("{}--->{},链接中,isReConnect:{}", getName(), target, isReConnect);
			ChannelFuture cf = doConnect(target, ctx -> {
//				if (ctx.channel().hasAttr(CancelReConnect)) {
//					//不可能到这里,超时的连接失败后又连接上
//					if (!ctx.channel().attr(CancelReConnect).get()) {
//						log.error("放弃重连,ch:{}", ctx.channel());
//						return;
//					}
//				}
//				log.info("{}--->{},断线触发重连:{}", getName(), target, ctx);
//				waitReconnect();
			});

			/*
			 * syncUninterruptibly()：不会被中断的sync()；
			 * awaitUninterruptibly()：不会被中断的await ()；
			 */
			try {
				cf.syncUninterruptibly();
			} catch (Throwable e) {
				log.error("{}--->{}, 连接失败, isReConnect:{},ch:{},{}", getName(), target, isReConnect, cf.channel(), e.getMessage());
				return false;
			}
			isConnect = cf.isDone() && cf.isSuccess();
			if (!isConnect) {
				log.warn("{}--->{}, 连接失败, isReConnect:{},ch:{}", getName(), target, isReConnect, cf.channel(), cf.cause());
//				cf.channel().attr(CancelReConnect).set(false);
				cf.channel().close();
				return false;
			}
			long connectTime = System.currentTimeMillis() - startConnectTime;
			log.info("{}--->{}, 连接成功, 连接耗时:{}毫秒 ,isReConnect:{},ch:{}", getName(), target, connectTime, isReConnect, cf.channel());
		} catch (Throwable e) {
			log.error(target + "," + e.getMessage(), e);
		}
		return isConnect;
	}

	protected abstract ChannelFuture doConnect(InetSocketAddress target, Consumer<ChannelHandlerContext> closeListener);

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
	public final boolean isReconnect() {
		return this.reconnect;
	}

	@Override
	public final long getReconnectTimeMills() {
		return timeMills;
	}

	protected final void waitReconnect() {
		if (isReconnect()) {
			Runnable task = () -> {
				log.info("{}--->{}, #重连开始……", getName(), address);
				boolean result = false;
				try {
					result = connect(address, true);
				} catch (Throwable e) {
					log.error(e.getMessage(), e);
				} finally {
					if (!result) {
						log.info("{}--->{},#重连失败,等待下一次重连", getName(), address);
						waitReconnect();
					}
				}
			};
//			getReconnectExecutor().schedule(task, getReconnectTimeMills(), TimeUnit.MILLISECONDS);//这里不能占用IO线程池
		}
	}
}
