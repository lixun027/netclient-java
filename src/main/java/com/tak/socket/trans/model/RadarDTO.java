/*
 * Created by 李迅 on 2023-05-22 11:21...
 */
package com.tak.socket.trans.model;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-22 11:21
 */
@Data
public class RadarDTO implements Serializable {

	private String name;

	private String sn;

	@JSONField(name = "equip_type")
	private Integer equipType;

	@JSONField(name = "msg_type")
	private Integer msgType;

	private List<RadarDroneDTO> info;

	@Data
	public static class RadarDroneDTO implements Serializable {

		/**
		 *
		 */
		@JSONField(name = "obj_id")
		private Long objId;

		/**
		 *
		 */
		@JSONField(name = "header_uid")
		private Long headerUid;

		/**
		 * 目标方位角（°）
		 */
		private Double azimuth;

		/**
		 *
		 */
		@JSONField(name = "obj_dist_interpol")
		private Double objDistInterpol;

		/**
		 * 目标俯仰角（°）
		 */
		private Double elevation;

		/**
		 * 目标径向运动速度（m/s）
		 */
		private Double velocity;

		/**
		 * 目标多普勒号，目标多普勒滤波器插值结果
		 */
		@JSONField(name = "doppler_chn")
		private Integer dopplerChn;

		/**
		 * 目标幅度值(dB)
		 */
		private Double mag;

		/**
		 * 目标速度模糊标记，0x00代表速度无模糊，0x01代表速度存在模糊
		 */
		private Integer ambiguous;

		/**
		 * 目标类别，
		 * 0x00：未识别
		 * 0x01：无人机
		 * 0x02：单兵
		 * 0x03：车辆
		 * 0x04：鸟类
		 * 0x05：直升机
		 * 其他无效。
		 */
		private Integer classification;

		/**
		 * 目标类别概率
		 */
		@JSONField(name = "classfy_prob")
		private Double classFyProb;

		/**
		 * 目标存在概率
		 */
		@JSONField(name = "existing_prob")
		private Double existingProb;

		/**
		 * 目标位移速度（m/s）
		 */
		@JSONField(name = "abs_vel")
		private Double absVel;

		/**
		 * 目标航向,目标运动方向与真北夹角(°)
		 */
		@JSONField(name = "orientation_angle")
		private Double orientationAngle;

		/**
		 * 目标周期数
		 */
		private Integer alive;

		/**
		 * 跟踪标识，0：TWS跟踪；1：TAS跟踪
		 */
		@JSONField(name = "tws_tas_flag")
		private Integer twsTasFlag;

		/**
		 * x相对坐标（m）
		 */
		private Double x;

		/**
		 * y相对坐标（m）
		 */
		private Double y;

		/**
		 * z相对坐标（m）
		 */
		private Double z;

		/**
		 * x方向相对速度（m/s）
		 */
		private Double vx;

		/**
		 * y方向相对速度（m/s）
		 */
		private Double vy;

		/**
		 * z方向相对速度（m/s）
		 */
		private Double vz;

		/**
		 * x方向相对加速度（m/s²）
		 */
		private Double ax;

		/**
		 * y方向相对加速度（m/s²）
		 */
		private Double ay;

		/**
		 * z方向相对加速度（m/s²），缩放因子：2^6
		 */
		private Double az;

		/**
		 * x相对坐标方差
		 */
		@JSONField(name = "x_variance")
		private Double xVariance;

		/**
		 * y相对坐标方差
		 */
		@JSONField(name = "y_variance")
		private Double yVariance;

		/**
		 * z相对坐标方差
		 */
		@JSONField(name = "z_variance")
		private Double zVariance;

		/**
		 * x方向相对速度方差
		 */
		@JSONField(name = "vx_variance")
		private Double vxVariance;

		/**
		 * y方向相对速度方差
		 */
		@JSONField(name = "vy_variance")
		private Double vyVariance;

		/**
		 * z方向相对速度方差
		 */
		@JSONField(name = "vz_variance")
		private Double vzVariance;

		/**
		 * x方向相对加速度方差
		 */
		@JSONField(name = "ax_variance")
		private Double axVariance;

		/**
		 * y方向相对加速度方差
		 */
		@JSONField(name = "ay_variance")
		private Double ayVariance;

		/**
		 * z方向相对加速度方差
		 */
		@JSONField(name = "az_variance")
		private Double azVariance;

		/**
		 * 目标航迹类型，0：暂态航迹；1：稳态航迹
		 */
		@JSONField(name = "state_type")
		private Integer stateType;

		/**
		 * 运动类型： 0：未知 1：静止 2：悬停 3：靠近 4：远离 其他待定义
		 */
		@JSONField(name = "motion_type")
		private Integer motionType;

		/**
		 * 目标预测帧数
		 */
		@JSONField(name = "forcast_frame_num")
		private Integer forCastFrameNum;

		/**
		 * 目标关联的检测点个数
		 */
		@JSONField(name = "association_num")
		private Integer associationNum;

		/**
		 * 关联检测点ID，bit位对应检测目标ID，0bit对应ID 0，31bit对应ID 31，位值为0时表示未关联对应ID目标，值为1时表示关联对应ID目标
		 */
		@JSONField(name = "assoc_bit0")
		private Integer assocBit0;

		/**
		 * 关联检测点ID，bit位对应检测目标ID，0bit对应ID 32，31bit对应ID 63，位值为0时表示未关联对应ID目标，值为1时表示关联对应ID目标
		 */
		@JSONField(name = "assoc_bit1")
		private Integer assocBit1;

		/**
		 * 备用，默认为0
		 */
		private Integer reserve;

		/**
		 * reserved2
		 */
		private Integer reserved2;

		/**
		 * 校验算法：CRC-16 Xmodem，校验数据长度从信息同步头到数据结束
		 */
		private Integer crc;

		/**
		 *
		 */
		@JSONField(name = "create_time")
		private String createTime;

		/**
		 *
		 */
		private String vendor;

		/**
		 *
		 */
		private String frequency;

		/**
		 *
		 */
		private String model;

		/**
		 *
		 */
		@JSONField(name = "is_whitelist")
		private Boolean isWhitelist;

		/**
		 *
		 */
		private Integer level;


	}

}
