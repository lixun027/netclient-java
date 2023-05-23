/*
 * Created by 李迅 on 2023-05-15 20:47...
 */
package com.tak.socket.net;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 20:47
 */
public interface NetClient {

	void start();

	void stop();

	String getName();

	void setName(String name);

	boolean isActive();

	// 主动停止的关闭重连
	void disableReconnect();

	boolean isReconnect();

	public long getReconnectTimeMills();

}
