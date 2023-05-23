/*
 * Created by 李迅 on 2023-05-18 13:43...
 */
package com.tak.socket.trans.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.tak.socket.net.config.IpAddressConfig;
import com.tak.socket.trans.domain.CotEvent;
import com.tak.socket.trans.enums.C2NetTypeEnum;
import com.tak.socket.trans.service.Reader;
import com.tak.socket.trans.service.Writer;
import com.tak.socket.util.HttpClientUtils;
import com.tak.socket.util.HttpResponse;
import com.tak.socket.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这是纯业务数据，与 socket 配置无关
 *
 * @author lixx
 * @version 1.0
 * @since 2023-05-18 13:43
 */
@Service
public class ReaderWriter {

	public Reader reade;

	public Writer write;

	ExecutorService executorService = Executors.newCachedThreadPool();

	@Autowired
	private DropDownReceiverTransService dropDownReceiverTransService;

	@Autowired
	public BlockingQueue<String> cotEventQueue;

	@Autowired
	private IpAddressConfig ipAddressConfig;

	private String sn;

	private Double lon;

	private Double lat;

	public void setReader() {

		reade = (data, type) -> {

			if (type == C2NetTypeEnum.WS_DRONE_ID) {
				/*executorService.execute(() -> {
					String currentDate = DateUtil.dateTimeFormat(DateUtil.Format.DATE_TIME_FORMAT2, System.currentTimeMillis());
					String staleDate = DateUtil.addMinutes(DateUtil.Format.DATE_TIME_FORMAT2, currentDate, 1);
					String uuid = UUID.randomUUID().toString();
					StringBuilder droneIdStr = new StringBuilder();
					droneIdStr.append("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
					droneIdStr.append("<event version='2.0' uid='").append(uuid).append("' type='a-f-A-skyfend-c2-droneId-list' time='").append(currentDate).append("' start='").append(currentDate).append("' stale='").append(staleDate).append("' how='h-g-i-g-o'>");
					droneIdStr.append("<point lat='0' lon='0' hae='9999999' ce='9999999' le='9999999'/>");
					droneIdStr.append("<detail>");
					droneIdStr.append("<ext>");
					droneIdStr.append(JSON.toJSONString(data));
					droneIdStr.append("</ext>");
					droneIdStr.append("</detail>");
					droneIdStr.append("</event>");
					try {
						cotEventQueue.put(droneIdStr.toString());
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				});*/

				// BOOMER
				// PARTRIDGE CNTRL
				List<CotEvent> cotEvents = dropDownReceiverTransService.transDroneIdService(data, "PARTRIDGE CNTRL", true);
				for (CotEvent cotEvent : cotEvents) {
					executorService.execute(() -> {
						String cotEventStr = XmlUtil.toStr(cotEvent);
						try {
							System.err.println("droneId数据 = " + cotEventStr);
							cotEventQueue.put(cotEventStr);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					});
				}
			} else if (type == C2NetTypeEnum.WS_RADAR_AIRPLANE) {
//				lon = 114.00656127929688D;
//				lat = 22.60003662109375D;

				JSONObject jsonObject = JSON.parseObject(data);
				sn = jsonObject.getString("sn");
				List<CotEvent> cotEvents = dropDownReceiverTransService.transRadarService(data, lon, lat);
				for (CotEvent cotEvent : cotEvents) {
					executorService.execute(() -> {
						String cotEventStr = XmlUtil.toStr(cotEvent);
						try {
							System.err.println("雷达飞机 = " + cotEventStr);
							cotEventQueue.put(cotEventStr);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					});
				}

			} else if (type == C2NetTypeEnum.WS_POSTURE) {
				executorService.execute(() -> {
//					sn="radar0000000000";
					if (StringUtils.isNotBlank(sn)) {
						JSONObject j = JSON.parseObject(data).getJSONObject("info");
						lon = j.getDouble("longitude");
						lat = j.getDouble("latitude");


						String url = ipAddressConfig.getHttpProto() + "://"  //
								+ ipAddressConfig.getHttpHost() + ":" //
								+ ipAddressConfig.getHttpPort() //
								+ ipAddressConfig.getHttpDeviceRadarConfig();
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("sn", sn);
						Map<String, String> headers = new HashMap<>();
						headers.put("Content-Type", "application/json");
						HttpResponse response = HttpClientUtils.getDelegate().post(url, jsonObject.toString(), headers);
						CotEvent cotEvents = dropDownReceiverTransService.transPostureService(data, response);
						String cotEventStr = XmlUtil.toStr(cotEvents);
						try {
							System.err.println("地雷图形 = " + cotEventStr);
							cotEventQueue.put(cotEventStr);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
				});
			}

		};

	}

	@SuppressWarnings("InfiniteLoopStatement")
	public void setWrite() {

		write = (channel) -> executorService.execute(() -> {

			while (true) {
				try {
					String cotEventStr = cotEventQueue.take();
//					System.err.println(cotEventStr);
					channel.writeAndFlush(cotEventStr);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

		});

	}

}
