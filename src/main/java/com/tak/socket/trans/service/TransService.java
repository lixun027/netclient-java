/*
 * Created by 李迅 on 2023-05-17 14:01...
 */
package com.tak.socket.trans.service;

import com.tak.socket.trans.domain.CotEvent;
import com.tak.socket.util.HttpResponse;

import java.util.List;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-17 14:01
 */
public interface TransService {

	/**
	 * @param msg         c2传过来的数据
	 * @param callSign    呼号，消息发送给指定的 ATAK 用户
	 * @param isBroadcast 是否广播，true:广播消息，false:指定呼号的消息
	 */
	List<CotEvent> transDroneIdService(String msg, String callSign, Boolean isBroadcast);

	List<CotEvent> transRadarService(String data, Double lon, Double lat);

	CotEvent transPostureService(String data, HttpResponse response);
}
