package com.tak.socket.net;

import io.netty.channel.ChannelOption;

public interface OptionConfig {

	<T> OptionConfig option(ChannelOption<T> option, T value);
}