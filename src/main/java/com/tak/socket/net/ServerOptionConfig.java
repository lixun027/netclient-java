package com.tak.socket.net;

import io.netty.channel.ChannelOption;

public interface ServerOptionConfig extends OptionConfig {

	<T> ServerOptionConfig option(ChannelOption<T> option, T value);

	<T> ServerOptionConfig childOption(ChannelOption<T> option, T value);
}