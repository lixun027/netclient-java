/*
 * Created by 李迅 on 2023-05-15 16:47...
 */
package com.tak.socket.net.session;

import io.netty.channel.group.ChannelGroup;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 16:47
 */
public interface StorageSession extends Session {

	ChannelGroup channelGroup();

}
