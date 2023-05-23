/*
 * Created by 李迅 on 2023-05-15 16:43...
 */
package com.tak.socket.net.server;

import com.tak.socket.net.ServerOptionConfig;
import com.tak.socket.net.handler.ShareableChannelInboundHandler;
import com.tak.socket.net.session.SessionFactory;
import com.tak.socket.net.session.StorageSession;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 16:43
 */
@Slf4j
public class NettyServer extends AbstractNettyServer {

	protected final ServerBootstrap serverBootstrap = new ServerBootstrap();

	protected final ChannelHandler handler;

	public NettyServer(String host, int port, int bossThreads, int ioThreads, ChannelHandler handler) {
		this(new InetSocketAddress(host, port), bossThreads, ioThreads, handler);
	}

	public NettyServer(InetSocketAddress local, int bossThreads, int ioThreads, ChannelHandler handler) {
		super(local);
		this.handler = handler;
		initServerBootstrap(bossThreads, ioThreads);
	}

	private void initServerBootstrap(int bossThreads, int workerThreads) {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		if (0 != bossThreads) {
			new NioEventLoopGroup(bossThreads);
		}
		if (0 != workerThreads) {
			new NioEventLoopGroup(workerThreads);
		}
		serverBootstrap.group(boss, worker);
		serverBootstrap.channel(NioServerSocketChannel.class);
	}

	@Override
	protected ChannelFuture doBind(InetSocketAddress local) {
		serverBootstrap.localAddress(local);
		initServerOptions(optionConfig());
		ChannelHandler fixedHandler = fixHandlerBeforeDoBootBind(handler);
		return doBootBind(local, fixedHandler);
	}

	protected ChannelFuture doBootBind(InetSocketAddress local, final ChannelHandler fixedHandler) {
		ChannelFuture cf;
		synchronized (serverBootstrap) {
			final CountDownLatch latch = new CountDownLatch(1);

			ChannelHandler childHandler = initLogHandlerAdapter(fixedHandler);
			serverBootstrap.childHandler(childHandler);
			cf = serverBootstrap.bind(local);
			cf.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) {
					latch.countDown();
				}
			});
			try {
				latch.await(3, TimeUnit.SECONDS);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return cf;
	}

	protected void initServerOptions(ServerOptionConfig config) {
		config.option(ChannelOption.SO_BACKLOG, 1024);
		config.childOption(ChannelOption.TCP_NODELAY, true);
		config.childOption(ChannelOption.SO_KEEPALIVE, true);
	}

	public ServerOptionConfig optionConfig() {
		return new ServerOptionConfig() {
			@Override
			public <T> ServerOptionConfig option(ChannelOption<T> option, T value) {
				serverBootstrap.option(option, value);
				return this;
			}

			@Override
			public <T> ServerOptionConfig childOption(ChannelOption<T> option, T value) {
				serverBootstrap.childOption(option, value);
				return this;
			}
		};
	}

	/**
	 * 子类可以重写，更改 handler
	 */
	protected ChannelHandler fixHandlerBeforeDoBootBind(ChannelHandler handler) {
		return handler;
	}

	protected ChannelHandler initLogHandlerAdapter(ChannelHandler init) {
		return new ShareableChannelInboundHandler() {
			@Override
			public void channelRegistered(ChannelHandlerContext ctx) {
				Channel ch = ctx.channel();
				manageChannel(ch);
				ch.pipeline().addLast(init);
				ctx.pipeline().remove(this);
				ctx.fireChannelRegistered();
			}
		};
	}

	protected void manageChannel(Channel channel) {
		StorageSession storageSession = SessionFactory.getStorageSession();
		storageSession.addChannelGroup(channel);
		channel.closeFuture().addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				storageSession.removeChannelGroup(future.channel());
			}
		});
	}

	public final void broadCast(Object message) {
		StorageSession storageSession = SessionFactory.getStorageSession();
		storageSession.channelGroup().writeAndFlush(message);
	}

//	@Override
//	public SendMessage getConnection(ChannelId id) {
//		StorageSession storageSession = SessionFactory.getStorageSession();
//		ChannelGroup channelGroup = storageSession.channelGroup();
//		if (channelGroup != null) {
//			for (Channel channel : channelGroup) {
//				if (NettyConnection.getChannelId(channel) == id) {
//					return NettyConnection.findConnection(channel);
//				}
//			}
//		}
//		return null;
//	}
}
