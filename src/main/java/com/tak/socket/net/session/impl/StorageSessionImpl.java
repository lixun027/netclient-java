/*
 * Created by 李迅 on 2023-05-15 16:49...
 */
package com.tak.socket.net.session.impl;

import com.tak.socket.net.session.StorageSession;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 16:49
 */
public class StorageSessionImpl implements StorageSession {

	// server 端
	protected final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

	@Override
	public boolean addChannelGroup(Channel channel) {
		if (null == channel) {
			return false;
		}
		return channelGroup.add(channel);
	}

	@Override
	public boolean removeChannelGroup(Channel channel) {
		if (null == channel) {
			return false;
		}
		return channelGroup.remove(channel);
	}

	@Override
	public ChannelGroup channelGroup() {
		return channelGroup;
	}
}
