/*
 * Created by 李迅 on 2023-05-16 10:35...
 */
package com.tak.socket.net.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-16 10:35
 */
@Data
@Component
@PropertySource("classpath:script/ip_address.properties")
@ConfigurationProperties(prefix = "remote")
public class IpAddressConfig {

	private String httpProto;

	private String httpHost;

	private String httpPort;

	private String httpDeviceRadarConfig;

	private String websocketProto;

	private String websocketHost;

	private String websocketPort;

	// droneId
	private String websocketDroneIdUrl;

	// 雷达无人机
	private String websocketRadarUrl;

	// 雷达姿态
	private String websocketPostureUrl;

	private String tcpHost;

	private String tcpPort;

}
