/*
 * Created by 李迅 on 2023-05-15 21:13...
 */
package com.tak.socket.net.client;

import com.tak.socket.net.OptionConfig;
import com.tak.socket.net.handler.ShareableChannelInboundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.function.Consumer;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 21:13
 */
@Slf4j
public class NettyClient extends AbstractNettyClient {

	protected final ChannelHandler handler;

	protected final Bootstrap bootstrap = new Bootstrap();

	protected final EventLoopGroup group = new NioEventLoopGroup();

	public NettyClient(String host, int port, ChannelHandler handler) {
		this(new InetSocketAddress(host, port), handler);
	}

	public NettyClient(InetSocketAddress targetAddress, ChannelHandler handler) {
		super(targetAddress);
		this.handler = handler;
		initBootstrap();
	}

	@Override
	protected EventLoopGroup getIoWorkers() {
		return group;
	}

	protected final ChannelFuture doConnect(InetSocketAddress targetAddress, Consumer<ChannelHandlerContext> closeListener) {
		ChannelHandler fixHandler = fixHandlerBeforeConnect(channelInitFix(handler));//修正handler
		return doBootstrapConnect(targetAddress, fixHandler, closeListener);
	}

	private ChannelHandler channelInitFix(final ChannelHandler handler) {
		return new ShareableChannelInboundHandler() {
			@Override
			public void channelRegistered(ChannelHandlerContext ctx) {
				Channel channel = ctx.channel();
				setChannel(channel);
				ctx.pipeline().addLast(handler);
				ctx.pipeline().remove(this);
				ctx.fireChannelRegistered();
			}
		};
	}

	protected ChannelHandler fixHandlerBeforeConnect(final ChannelHandler handler) {
		return handler;
	}

	protected ChannelFuture doBootstrapConnect(InetSocketAddress target, final ChannelHandler init, Consumer<ChannelHandlerContext> closeListener) {
		ChannelFuture cf;
		synchronized (bootstrap) {
			ChannelHandler handler = initHandlerAdapter(init, closeListener);
			bootstrap.handler(handler);
			cf = bootstrap.connect(target);
		}
		return cf;
	}


	//

	private void initBootstrap() {
		bootstrap.group(group);
		bootstrap.channel(NioSocketChannel.class);
		initBootstrapOptions(optionConfig());
	}

	protected void initBootstrapOptions(OptionConfig config) {
		//配置连接超时
		config.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000);
	}

	public OptionConfig optionConfig() {
		return new OptionConfig() {
			@Override
			public <T> OptionConfig option(ChannelOption<T> option, T value) {
				bootstrap.option(option, value);
				return this;
			}
		};
	}


	protected ChannelHandler initHandlerAdapter(ChannelHandler init, Consumer<ChannelHandlerContext> closeListener) {
		return new ShareableChannelInboundHandler() {
			@Override
			public void channelRegistered(ChannelHandlerContext ctx) {
				Channel ch = ctx.channel();

				ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
					@Override
					public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
						log.info("channelRegistered:{}", ctx.channel());
						super.channelRegistered(ctx);
					}

					@Override
					public void channelActive(ChannelHandlerContext ctx) throws Exception {
						log.info("channelActive:{}", ctx.channel());
						super.channelActive(ctx);
					}

					@Override
					public void channelInactive(ChannelHandlerContext ctx) throws Exception {
						log.info("channelInactive:{}", ctx.channel());
						if (closeListener != null) {
							try {
								closeListener.accept(ctx);
							} catch (Throwable e) {
								log.error(e.getMessage(), e);
							}
						}
						super.channelInactive(ctx);
					}

					@Override
					public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
						log.info("channelUnregistered:{}", ctx.channel());
						super.channelUnregistered(ctx);
					}
				});
				ch.pipeline().addLast(init);
				ctx.pipeline().remove(this);
				ctx.fireChannelRegistered();
			}
		};
	}
}
