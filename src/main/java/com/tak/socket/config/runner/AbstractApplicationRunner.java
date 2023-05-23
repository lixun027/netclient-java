/*
 * Created by 李迅 on 2023-05-18 13:56...
 */
package com.tak.socket.config.runner;

import com.tak.socket.net.config.IpAddressConfig;
import com.tak.socket.trans.service.impl.ReaderWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-18 13:56
 */
@Component
public abstract class AbstractApplicationRunner implements ApplicationRunner {

	@Autowired
	public IpAddressConfig ipAddressConfig;

	@Autowired
	public ReaderWriter readerWriter;

}
