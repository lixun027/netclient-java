/*
 * Created by 李迅 on 2023-05-17 14:02...
 */
package com.tak.socket.trans.service.impl;

import com.alibaba.fastjson2.JSON;
import com.tak.socket.trans.C2ToCotEventTrans;
import com.tak.socket.trans.domain.CotEvent;
import com.tak.socket.trans.model.DroneDTO;
import com.tak.socket.trans.model.PostureDTO;
import com.tak.socket.trans.model.RadarDTO;
import com.tak.socket.trans.service.TransService;
import com.tak.socket.util.HttpResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-17 14:02
 */
@Service
public class DropDownReceiverTransService implements TransService {


	@Override
	public List<CotEvent> transDroneIdService(String msg, String callSign, Boolean isBroadcast) {
		DroneDTO droneDTO = JSON.parseObject(msg, DroneDTO.class);
		return C2ToCotEventTrans.getDroneIdCoTEvent(droneDTO, callSign, isBroadcast);
	}

	@Override
	public List<CotEvent> transRadarService(String data, Double lon, Double lat) {
		RadarDTO radarDTO = JSON.parseObject(data, RadarDTO.class);
		return C2ToCotEventTrans.getRadarCotEvent(radarDTO, lon, lat);
	}

	@Override
	public CotEvent transPostureService(String data, HttpResponse response) {
		PostureDTO postureDTO = JSON.parseObject(data, PostureDTO.class);
		return C2ToCotEventTrans.getPostureCotEvent(postureDTO, response);
	}
}
