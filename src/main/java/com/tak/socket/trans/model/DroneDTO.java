/*
 * Created by 李迅 on 2023-05-16 20:05...
 */
package com.tak.socket.trans.model;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-16 20:05
 */
@Data
public class DroneDTO implements Serializable {

	private String name;

	private String sn;

	@JSONField(name = "equip_type")
	private Integer equipType;

	@JSONField(name = "msg_type")
	private Integer msgType;

	@JSONField(name = "info")
	private DroneInfoDTO droneInfo;

	@Data
	public static class DroneInfoDTO implements Serializable {

		/**
		 * 时间戳
		 */
		private Long timeStamp;

		/**
		 * 0~100% 电量百分比
		 */
		private Integer electricity;

		/**
		 * 设备号
		 */
		private String sn;

		/**
		 *
		 */
		@JSONField(name = "is_online")
		private Integer isOnline;

		private List<DroneInfo2DTO> info;

	}

	@Data
	public static class DroneInfo2DTO implements Serializable {

		/**
		 *
		 */
		private String name;

		/**
		 * 无人机类型
		 */
		@JSONField(name = "product_type")
		private String productType;

		/**
		 * 字符串 无人机名称
		 */
		@JSONField(name = "drone_name")
		private String droneName;

		/**
		 * 序列号
		 */
		@JSONField(name = "serial_num")
		private String serialNum;

		/**
		 * LSB无人机经度(/1e7/pi*180)
		 */
		@JSONField(name = "drone_longitude")
		private Double droneLongitude;

		/**
		 * LSB无人机纬度(/1e7/pi*180)
		 */
		@JSONField(name = "drone_latitude")
		private Double droneLatitude;

		/**
		 * LSB无人机相对地面高度(0.1m)
		 */
		@JSONField(name = "drone_height")
		private Double droneHeight;

		/**
		 * LSB无人机角度(0.01deg)
		 */
		@JSONField(name = "drone_yaw_angle")
		private Double droneYawAngle;

		/**
		 * LSB无人机绝对速度(0.01m/s)
		 */
		@JSONField(name = "drone_speed")
		private Integer droneSpeed;

		/**
		 * LSB无人机垂直速度(0.01m/s)
		 */
		@JSONField(name = "drone_vertical_speed")
		private Integer droneVerticalSpeed;

		/**
		 *
		 */
		@JSONField(name = "operator_longitude")
		private Double operatorLongitude;

		/**
		 *
		 */
		@JSONField(name = "operator_latitude")
		private Double operatorLatitude;

		/**
		 * LSB目标水平角(0.01°),无效值0x7fffffff
		 */
		@JSONField(name = "drone_horizon")
		private Float droneHorizon;

		/**
		 * LSB目标俯仰角(0.01°) ,无效值0x7fffffff
		 */
		@JSONField(name = "drone_pitch")
		private Float dronePitch;

		/**
		 * // LSB 无人机信号频率(mHz)   //  0：打击所有范围    1：打击小于2G    2：打击2-4G  3：打击4-6G  4：打击2G、2-4G   5：打击2-4G 、4-6G  6：打击2G、4-6G
		 */
		private Float freq;

		/**
		 * LSB 无人机与DroneId的距离(m)
		 */
		private Integer distance;

		/**
		 * LSB 危险等级
		 */
		@JSONField(name = "danger_levels")
		private Integer dangerLevels;

		/**
		 * LSB无人机航点经度(/1e7/pi*180)
		 */
		@JSONField(name = "waypoint_longitude")
		private Double waypointLongitude;

		/**
		 * LSB无人机航点纬度(/1e7/pi*180)
		 */
		@JSONField(name = "waypoint_latitude")
		private Double waypointLatitude;

	}

}

