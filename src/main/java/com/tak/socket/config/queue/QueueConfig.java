/*
 * Created by 李迅 on 2023-05-17 19:19...
 */
package com.tak.socket.config.queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-17 19:19
 */
@Configuration
public class QueueConfig {

	@Bean
	public BlockingQueue<String> cotEventQueue() {
		return new LinkedBlockingQueue<>(10000);
	}

}
