///*
// * Created by 李迅 on 2023-05-15 19:58...
// */
//package com.tak.websocket.net;
//
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelId;
//import io.netty.util.AttributeKey;
//
///**
// * @author lixx
// * @version 1.0
// * @since 2023-05-15 19:58
// */
//public class NettyConnection implements SendMessage {
//
//	public static AttributeKey<NettyConnection> CHANNEL_KEY = AttributeKey.newInstance("NettyConnection");
//
//	protected final ChannelHandlerContext ctx;
//
//	protected final Channel channel;
//
//	protected ChannelId channelId;
//
//	public NettyConnection(ChannelHandlerContext ctx) {
//		this.ctx = ctx;
//		this.channel = ctx.channel();
//		this.channelId = ctx.channel().id();
//		channel.attr(CHANNEL_KEY).set(this);
//	}
//
//	@Override
//	public void writeAndFlush(Object obj) {
//		channel.writeAndFlush(obj);
//	}
//
//	public static ChannelId getChannelId(Channel channel) {
//		return channel.id();
//	}
//
//	public static NettyConnection findConnection(Channel channel) {
//		return channel.attr(CHANNEL_KEY).get();
//	}
//
//
//}
