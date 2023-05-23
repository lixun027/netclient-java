package com.tak.socket.trans.service;

import io.netty.channel.Channel;

public interface Writer {
	void write(Channel channel);
}