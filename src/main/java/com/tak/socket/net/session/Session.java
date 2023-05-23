/*
 * Created by 李迅 on 2023-05-15 16:45...
 */
package com.tak.socket.net.session;

import io.netty.channel.Channel;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 16:45
 */
public interface Session {

	boolean addChannelGroup(Channel channel);

	boolean removeChannelGroup(Channel channel);
	
}
