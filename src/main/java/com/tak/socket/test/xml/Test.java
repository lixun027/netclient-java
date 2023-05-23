package com.tak.socket.test.xml;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.tak.socket.trans.domain.CotEvent;
import com.tak.socket.trans.enums.CotTypeEnum;
import com.tak.socket.util.*;

import javax.sound.midi.Track;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试类
 */
public class Test {

	public void test1() {
		Message message = new Message();
		message.setAbc("123");
		MsgHeader header = new MsgHeader();
		MsgBody body = new MsgBody();
		header.setId("1");
		header.setType("A");
		body.setDate("20210506");
		body.setContent("qwerty");
		message.setHeader(header);
		message.setBody(body);

		String str = XmlUtil.toStr(message);
		System.err.println(str);
	}

	public static void test2() {
		String current = DateUtil.dateTimeFormat(DateUtil.Format.DATE_TIME_FORMAT2, System.currentTimeMillis());
		CotEvent event = new CotEvent();
		event.setVersion(2F);
		event.setUid("123");
		event.setType(CotTypeEnum.TYPE_2.getType());
		event.setTime(current);
		event.setStart(current);
		event.setStale(current);
		event.setHow(CotTypeEnum.HOW_1.getType());

		CotEvent.Point point = new CotEvent.Point();
		point.setLat(22.547D);
		point.setLon(114.255947);
		point.setHae(9999999.0);
		point.setCe(9999999.0);
		point.setLe(9999999.0);

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
		contact.setCallSign("aaa");
		detail.setContact(contact);
		CotEvent.Uid uid = new CotEvent.Uid();
		detail.setUid(uid);
		CotEvent.Special special = new CotEvent.Special();
		special.setCount(0);
		detail.setSpecial(special);

		CotEvent.Link link = new CotEvent.Link();
		link.setType(CotTypeEnum.TYPE_3.getType());
		link.setUid("8c820185-eea6-4475-8eaa-fac064773d40");
		link.setParentCallSign("ZS");
		link.setRelation("p-p");
		link.setProductionTime(current);
		detail.setLink(link);

		CotEvent.UserIcon userIcon = new CotEvent.UserIcon();
		userIcon.setIconSetPath("COT_MAPPING_2525C/a-n/a-n-G");
		detail.setUserIcon(userIcon);

		CotEvent.Marti marti = new CotEvent.Marti();
		CotEvent.Dest dest = new CotEvent.Dest();
		dest.setCallSign("BOOMER");
		marti.setDest(dest);
		detail.setMarti(marti);

		CotEvent.Height height = new CotEvent.Height();
		height.setValue(123D);
		height.setUnit("meters");
		detail.setHeight(height);
		CotEvent.HeightUnit heightUnit = new CotEvent.HeightUnit();
		heightUnit.setValue(1);
		detail.setHeightUnit(heightUnit);

		CotEvent.Model model = new CotEvent.Model();
		model.setType("vehicle");
		model.setName("aaaaa");
		model.setCategory("Aircraft");
		model.setOutline(false);
		detail.setModel(model);

		CotEvent.Track track = new CotEvent.Track();
		track.setCourse(111D);
		detail.setTrack(track);

		detail.setExt("aaaaaa");
		event.setDetail(detail);

		String str = XmlUtil.toStr(event);
		System.err.println(str);

////
//		StringWriter sw = new StringWriter();
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(event.getClass());
//			Marshaller marshaller = jaxbContext.createMarshaller();
//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//			marshaller.marshal(event.getClass(), sw);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println(sw);
	}

	public static void main(String[] args) {

		test3();

	}

	public static void test3() {
		LatLngTools latLngTools = new LatLngTools();
		LatLngTools.Lation lation = latLngTools.transform2Mars(223, 202);
		System.err.println(lation.toString());
	}
}
