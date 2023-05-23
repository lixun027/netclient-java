/*
 * Created by 李迅 on 2023-05-17 16:14...
 */
package com.tak.socket.trans;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.tak.socket.trans.domain.CotEvent;
import com.tak.socket.trans.enums.CotHeightUnitEnum;
import com.tak.socket.trans.enums.CotTypeEnum;
import com.tak.socket.trans.model.DroneDTO;
import com.tak.socket.trans.model.Place;
import com.tak.socket.trans.model.PostureDTO;
import com.tak.socket.trans.model.RadarDTO;
import com.tak.socket.util.DateUtil;
import com.tak.socket.util.HttpResponse;
import com.tak.socket.util.Xy2l;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-17 16:14
 */
public class C2ToCotEventTrans {

	public static List<CotEvent> getDroneIdCoTEvent(DroneDTO dto, String callSign, Boolean isBroadcast) {
		if (null == dto) {
			return null;
		}
		List<CotEvent> list = new CopyOnWriteArrayList<>();

		String currentDate = DateUtil.dateTimeFormat(DateUtil.Format.DATE_TIME_FORMAT2, System.currentTimeMillis());
		String staleDate = DateUtil.addMinutes(DateUtil.Format.DATE_TIME_FORMAT2, currentDate, 1);

		String timeStamp = DateUtil.dateTimeFormat(DateUtil.Format.DATE_TIME_FORMAT2, dto.getDroneInfo().getTimeStamp() * 1000);

		List<DroneDTO.DroneInfo2DTO> infoList = dto.getDroneInfo().getInfo();
		for (DroneDTO.DroneInfo2DTO info : infoList) {

			CotEvent event = new CotEvent();
			event.setVersion(2F);
			event.setUid(dto.getSn() + "^" + dto.getDroneInfo().getSn() + "^" + info.getSerialNum());
			event.setType(CotTypeEnum.TYPE_5.getType());
			event.setTime(currentDate);
			event.setStart(currentDate);
			event.setStale(staleDate);
			event.setHow(CotTypeEnum.HOW_1.getType());

			CotEvent.Point point = new CotEvent.Point();
			point.setLat(info.getDroneLatitude());
			point.setLon(info.getDroneLongitude());
			point.setHae(info.getDroneHeight());
			point.setCe(9999999D);
			point.setLe(9999999D);

			event.setPoint(point);

			CotEvent.Detail detail = new CotEvent.Detail();
			CotEvent.Status status = new CotEvent.Status();
			status.setReadiness(true);
			detail.setStatus(status);
			CotEvent.Archive archive = new CotEvent.Archive();
			CotEvent.Remarks remarks = new CotEvent.Remarks();
			archive.setRemarks(remarks);
			detail.setArchive(archive);
			CotEvent.Contact contact = new CotEvent.Contact();
			contact.setCallSign(info.getDroneName());
			detail.setContact(contact);
			CotEvent.Uid uid = new CotEvent.Uid();
			detail.setUid(uid);
			CotEvent.Special special = new CotEvent.Special();
			special.setCount(0);
			detail.setSpecial(special);

			CotEvent.Link link = new CotEvent.Link();
			link.setType(CotTypeEnum.TYPE_3.getType());
			link.setUid("8c820185-eea6-4475-8eaa-fac064773d41");
			link.setParentCallSign("ZS");
			link.setRelation("p-p");
			link.setProductionTime(timeStamp);
			detail.setLink(link);

			CotEvent.UserIcon userIcon = new CotEvent.UserIcon();
//			userIcon.setIconSetPath("f7f71666-8b28-4b57-9fbb-e38e61d33b79/Google/airports.png");
//			userIcon.setIconSetPath("f7f71666-8b28-4b57-9fbb-e38e61d33b79/Google/airports.png");
//			detail.setUserIcon(userIcon);

			if (!isBroadcast) {
				// 单独发送
				CotEvent.Marti marti = new CotEvent.Marti();
				CotEvent.Dest dest = new CotEvent.Dest();
				dest.setCallSign(callSign);
				marti.setDest(dest);
				detail.setMarti(marti);
			}

			CotEvent.Height height = new CotEvent.Height();
			height.setValue(info.getDroneHeight());
			height.setUnit(CotHeightUnitEnum.M.getSUnit());
			detail.setHeight(height);
			CotEvent.HeightUnit heightUnit = new CotEvent.HeightUnit();
			heightUnit.setValue(CotHeightUnitEnum.M.getIUnit());
			detail.setHeightUnit(heightUnit);


//			CotEvent.Model model = new CotEvent.Model();
//			model.setType("vehicle");
//			model.setName("AV-8B");
//			model.setCategory("Aircraft");
//			model.setOutline(false);
//			detail.setModel(model);

//			CotEvent.Track track = new CotEvent.Track();
//			track.setCourse(info.getDroneYawAngle() * 100);
//			detail.setTrack(track);

//			CotEvent.ShowLabel showLabel = new CotEvent.ShowLabel();
//			showLabel.setValue(false);
//			detail.setShowLabel(showLabel);


			detail.setExt(JSON.toJSONString(info));

			event.setDetail(detail);
			list.add(event);
		}

		return list;
	}

	public static List<CotEvent> getRadarCotEvent(RadarDTO dto, Double lon, Double lat) {
		if (null == dto || null == lon || null == lat) {
			return new ArrayList<>();
		}

		String currentDate = DateUtil.dateTimeFormat(DateUtil.Format.DATE_TIME_FORMAT2, System.currentTimeMillis());
		String staleDate = DateUtil.addMinutes(DateUtil.Format.DATE_TIME_FORMAT2, currentDate, 1);

		List<CotEvent> list = new CopyOnWriteArrayList<>();
		List<RadarDTO.RadarDroneDTO> infoList = dto.getInfo();
		for (RadarDTO.RadarDroneDTO info : infoList) {

			CotEvent event = new CotEvent();
			event.setVersion(2F);
			event.setUid(String.valueOf(info.getObjId()));
			event.setType(CotTypeEnum.TYPE_7.getType());
			event.setTime(currentDate);
			event.setStart(currentDate);
			event.setStale(staleDate);
			event.setHow(CotTypeEnum.HOW_1.getType());

			Place place = Xy2l.trans(lon, lat, info.getX(), info.getY());
			CotEvent.Point point = new CotEvent.Point();
			point.setLon(place.getX());
			point.setLat(place.getY());
			point.setHae(9999999D);
			point.setCe(9999999D);
			point.setLe(9999999D);

			event.setPoint(point);

			CotEvent.Detail detail = new CotEvent.Detail();
			CotEvent.Status status = new CotEvent.Status();
			status.setReadiness(true);
			detail.setStatus(status);
			CotEvent.Archive archive = new CotEvent.Archive();
			CotEvent.Remarks remarks = new CotEvent.Remarks();
			archive.setRemarks(remarks);
			detail.setArchive(archive);
			CotEvent.Contact contact = new CotEvent.Contact();
//			contact.setCallSign(info.getDroneName());
			detail.setContact(contact);
			CotEvent.Uid uid = new CotEvent.Uid();
			detail.setUid(uid);
			CotEvent.Special special = new CotEvent.Special();
			special.setCount(0);
			detail.setSpecial(special);

			CotEvent.Link link = new CotEvent.Link();
			link.setType(CotTypeEnum.TYPE_3.getType());
			link.setUid("8c820185-eea6-4475-8eaa-fac064773d41");
			link.setParentCallSign("ZS");
			link.setRelation("p-p");
			link.setProductionTime(currentDate);
			detail.setLink(link);

//			CotEvent.Height height = new CotEvent.Height();
//			height.setValue(info.getDroneHeight());
//			height.setUnit(CotHeightUnitEnum.M.getSUnit());
//			detail.setHeight(height);
//			CotEvent.HeightUnit heightUnit = new CotEvent.HeightUnit();
//			heightUnit.setValue(CotHeightUnitEnum.M.getIUnit());
//			detail.setHeightUnit(heightUnit);

			detail.setExt(JSON.toJSONString(info));

			event.setDetail(detail);
			list.add(event);

		}

		return list;
	}

	public static CotEvent getPostureCotEvent(PostureDTO dto, HttpResponse response) {
		if (null == dto) {
			return null;
		}

		String currentDate = DateUtil.dateTimeFormat(DateUtil.Format.DATE_TIME_FORMAT2, System.currentTimeMillis());
		String staleDate = DateUtil.addMinutes(DateUtil.Format.DATE_TIME_FORMAT2, currentDate, 1);

		CotEvent event = new CotEvent();
		PostureDTO.PostureInfoDTO info = dto.getInfo();
		event.setVersion(2F);
		event.setUid(dto.getSn());
		event.setType(CotTypeEnum.TYPE_8.getType());
		event.setTime(currentDate);
		event.setStart(currentDate);
		event.setStale(staleDate);
		event.setHow(CotTypeEnum.HOW_1.getType());

		CotEvent.Point point = new CotEvent.Point();
		point.setLat(info.getLatitude());
		point.setLon(info.getLongitude());
		point.setHae(info.getAltitude());
		point.setCe(9999999D);
		point.setLe(9999999D);

		event.setPoint(point);

		CotEvent.Detail detail = new CotEvent.Detail();
		CotEvent.Contact contact = new CotEvent.Contact();
		contact.setCallSign(dto.getSn());
		detail.setContact(contact);

		CotEvent.Link link = new CotEvent.Link();
		link.setType(CotTypeEnum.TYPE_3.getType());
		link.setUid("8c820185-eea6-4475-8eaa-fac064773d41");
		link.setParentCallSign("ZS");
		link.setRelation("p-p");
		link.setProductionTime(currentDate);
		detail.setLink(link);

		CotEvent.Sensor sensor = new CotEvent.Sensor();
		JSONObject jsonObject = JSONObject.from(response);
		Object o = jsonObject.getJSONObject("body").getJSONObject("data").get("azi_scan_scope");
		sensor.setFov((Integer) o);
		sensor.setFovBlue(0.498039215686275D);
		sensor.setDisplayMagneticReference(0);
		sensor.setRange(900);
		sensor.setFovGreen(0.498039215686275D);
		sensor.setFovAlpha(0.309803921568627D);
		sensor.setFovRed(0);
		sensor.setAzimuth(info.getHeading());
		sensor.setStrokeColor(922746879L);

		detail.setSensor(sensor);


		event.setDetail(detail);

		return event;

	}
}
