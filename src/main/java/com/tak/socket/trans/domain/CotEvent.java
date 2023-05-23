/*
 * Created by 李迅 on 2023-05-17 14:19...
 */
package com.tak.socket.trans.domain;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import javax.xml.soap.Detail;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-17 14:19
 */
@Setter
@XmlType(propOrder = {"version", "uid", "type", "time", "start", "stale", "how", "point", "detail"})
@XmlRootElement(name = "event")
public class CotEvent {

	private Float version;

	private String uid;

	private String type;

	private String time;

	// 开始时间
	private String start;

	private String stale;

	private String how;

	private Point point;

	private Detail detail;

	@Setter
	@XmlType(propOrder = {"lat", "lon", "hae", "ce", "le"})
	@XmlRootElement(name = "point")
	public static class Point {

		// 纬度
		private Double lat;

		// 经度
		private Double lon;

		private Double hae;

		private Double ce;

		private Double le;

		@XmlAttribute
		public Double getLat() {
			return lat;
		}

		@XmlAttribute
		public Double getLon() {
			return lon;
		}

		@XmlAttribute
		public Double getHae() {
			return hae;
		}

		@XmlAttribute
		public Double getCe() {
			return ce;
		}

		@XmlAttribute
		public Double getLe() {
			return le;
		}

	}

	@Setter
	@XmlType(propOrder = {"status", "archive", "contact", "uid", "special", "link"
			, "userIcon", "marti", "height", "heightUnit", "model", "track"
			, "showLabel", "sensor"
			, "ext"
	})
	@XmlRootElement(name = "detail")
	public static class Detail {

		private Status status;

		private Archive archive;

		private Contact contact;

		private Uid uid;

		private Special special;

		private Link link;

		private UserIcon userIcon;

		private Marti marti;

		private Height height;

		private HeightUnit heightUnit;

		private Model model;

		private Track track;

		private ShowLabel showLabel;

		private Sensor sensor;

		private String ext;

		public Status getStatus() {
			return status;
		}

		public Archive getArchive() {
			return archive;
		}

		public Contact getContact() {
			return contact;
		}

		public Uid getUid() {
			return uid;
		}

		@XmlElement(name = "__special")
		public Special getSpecial() {
			return special;
		}

		public Link getLink() {
			return link;
		}

		@XmlElement(name = "usericon")
		public UserIcon getUserIcon() {
			return userIcon;
		}

		public Marti getMarti() {
			return marti;
		}

		public Height getHeight() {
			return height;
		}

		@XmlElement(name = "height_unit")
		public HeightUnit getHeightUnit() {
			return heightUnit;
		}

		public Model getModel() {
			return model;
		}

		public Track getTrack() {
			return track;
		}

		public ShowLabel getShowLabel() {
			return showLabel;
		}

		public Sensor getSensor() {
			return sensor;
		}

		public String getExt() {
			return ext;
		}
	}

	@Setter
	@XmlType
	@XmlRootElement(name = "status")
	public static class Status {

		private Boolean readiness;

		@XmlAttribute
		public Boolean getReadiness() {
			return readiness;
		}
	}

	@Setter
	@XmlType(propOrder = {"remarks"})
	@XmlRootElement(name = "archive")
	public static class Archive {

		private Remarks remarks;

		public Remarks getRemarks() {
			return remarks;
		}
	}


	@Setter
	@XmlType
	@XmlRootElement(name = "remarks")
	public static class Remarks {


	}

	@Setter
	@XmlType(propOrder = {"callSign"})
	@XmlRootElement(name = "contact")
	public static class Contact {

		// 事件 呼号
		private String callSign;

		@XmlAttribute(name = "callsign")
		public String getCallSign() {
			return callSign;
		}
	}

	@Setter
	@XmlType
	@XmlRootElement(name = "uid")
	public static class Uid {


	}

	@Setter
	@XmlType(propOrder = {"count"})
	@XmlRootElement(name = "special")
	public static class Special {

		private Integer count;

		@XmlAttribute
		public Integer getCount() {
			return count;
		}
	}

	@Setter
	@XmlType(propOrder = {"type", "uid", "parentCallSign", "relation", "productionTime"})
	@XmlRootElement(name = "link")
	public static class Link {

		private String type;

		// 发送人用户id
		private String uid;

		// 发送人呼号
		private String parentCallSign;

		private String relation;

		// 生产时间
		private String productionTime;

		@XmlAttribute
		public String getType() {
			return type;
		}

		@XmlAttribute
		public String getUid() {
			return uid;
		}

		@XmlAttribute(name = "parent_callsign")
		public String getParentCallSign() {
			return parentCallSign;
		}

		@XmlAttribute
		public String getRelation() {
			return relation;
		}

		@XmlAttribute(name = "production_time")
		public String getProductionTime() {
			return productionTime;
		}
	}

	@Setter
	@XmlType(propOrder = {"iconSetPath"})
	@XmlRootElement(name = "usericon")
	public static class UserIcon {

		private String iconSetPath;

		@XmlAttribute(name = "iconsetpath")
		public String getIconSetPath() {
			return iconSetPath;
		}
	}

	@Setter
	@Getter
	@XmlType(propOrder = {"dest"})
	@XmlRootElement(name = "marti")
	public static class Marti {

		private Dest dest;

	}

	@Setter
	@XmlType(propOrder = {"callSign"})
	@XmlRootElement(name = "dest")
	public static class Dest {

		// 收件人呼号
		private String callSign;

		@XmlAttribute(name = "callsign")
		public String getCallSign() {
			return callSign;
		}
	}

	@Setter
	@XmlType(propOrder = {"value", "unit"})
	@XmlRootElement(name = "height")
	public static class Height {

		private Double value;

		private String unit;


		@XmlAttribute
		public Double getValue() {
			return value;
		}

		@XmlAttribute
		public String getUnit() {
			return unit;
		}

		@XmlValue
		public Double getValue2() {
			return value;
		}
	}

	@Setter
	@XmlRootElement(name = "height_unit")
	public static class HeightUnit {

		private Integer value;

		@XmlValue
		public Integer getValue() {
			return value;
		}
	}

	/**
	 * 飞机类型
	 */
	@Setter
	@XmlType(propOrder = {"type", "name", "category", "outline"})
	@XmlRootElement(name = "model")
	public static class Model {

		private String type;

		private String name;

		private String category;

		private Boolean outline;

		@XmlAttribute
		public String getType() {
			return type;
		}

		@XmlAttribute
		public String getName() {
			return name;
		}

		@XmlAttribute
		public String getCategory() {
			return category;
		}

		@XmlAttribute
		public Boolean getOutline() {
			return outline;
		}
	}

	@Setter
	@XmlType(propOrder = {"course"})
	@XmlRootElement(name = "track")
	public static class Track {

		private Double course;

		@XmlAttribute
		public Double getCourse() {
			return course;
		}
	}

	@Setter
	@XmlType(propOrder = {"value"})
	@XmlRootElement(name = "showLabel")
	public static class ShowLabel {

		// 是否显示 名称，false:不显示，true:显示
		private Boolean value;

		@XmlAttribute
		public Boolean getValue() {
			return value;
		}
	}

	@Setter
	@XmlType(propOrder = {"fov", "fovBlue", "displayMagneticReference", "range", "fovGreen", "fovAlpha", "fovRed", "azimuth", "strokeColor"})
	@XmlRootElement(name = "sensor")
	public static class Sensor {

		private Integer fov;

		private Double fovBlue;

		private Integer displayMagneticReference;

		private Integer range;

		private Double fovGreen;

		private Double fovAlpha;

		private Integer fovRed;

		private Integer azimuth;

		private Long strokeColor;

		@XmlAttribute
		public Integer getFov() {
			return fov;
		}

		@XmlAttribute
		public Double getFovBlue() {
			return fovBlue;
		}

		@XmlAttribute
		public Integer getDisplayMagneticReference() {
			return displayMagneticReference;
		}

		@XmlAttribute
		public Integer getRange() {
			return range;
		}

		@XmlAttribute
		public Double getFovGreen() {
			return fovGreen;
		}

		@XmlAttribute
		public Double getFovAlpha() {
			return fovAlpha;
		}

		@XmlAttribute
		public Integer getFovRed() {
			return fovRed;
		}

		@XmlAttribute
		public Integer getAzimuth() {
			return azimuth;
		}

		@XmlAttribute
		public Long getStrokeColor() {
			return strokeColor;
		}
	}


	@XmlAttribute
	public Float getVersion() {
		return version;
	}

	@XmlAttribute
	public String getUid() {
		return uid;
	}

	@XmlAttribute
	public String getType() {
		return type;
	}

	@XmlAttribute
	public String getTime() {
		return time;
	}

	@XmlAttribute
	public String getStart() {
		return start;
	}

	@XmlAttribute
	public String getStale() {
		return stale;
	}

	@XmlAttribute
	public String getHow() {
		return how;
	}

	public Point getPoint() {
		return point;
	}

	public Detail getDetail() {
		return detail;
	}
}
