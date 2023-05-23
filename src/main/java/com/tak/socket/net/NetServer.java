/*
 * Created by 李迅 on 2023-05-15 16:34...
 */
package com.tak.socket.net;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 16:34
 */
public interface NetServer {

	boolean start();

	void stop();

	String getName();

	void setName(String name);

//	public SendMessage getConnection(ChannelId id);

	void broadCast(Object message);

	boolean isActive();
}
