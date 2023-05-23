/*
 * Created by 李迅 on 2023-05-22 14:39...
 */
package com.tak.socket.trans.model;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-22 14:39
 */
@Data
public class PostureDTO implements Serializable {

	private String name;

	private String sn;

	@JSONField(name = "equip_type")
	private Integer equipType;

	@JSONField(name = "msg_type")
	private Integer msgType;

	private PostureInfoDTO info;

	@Data
	public static class PostureInfoDTO {

		/**
		 *
		 */
		private Integer reserved;

		/**
		 * 航向角度，与正北的夹角. [0,360]
		 * 硬件给出结果为 左正右负；在使用时需要取反 -heading
		 */
		private Integer heading;

		/**
		 * 纵向角度 [-30,+30]
		 */
		private Integer pitching;

		/**
		 * 横滚角度 [-45,+45]
		 */
		private Integer rolling;

		/**
		 * 经度
		 */
		private Double longitude;

		/**
		 * 纬度
		 */
		private Double latitude;

		/**
		 * 海拔高度 m
		 */
		private Double altitude;

		/**
		 * 导航速度（m/s）
		 */
		@JSONField(name = "velocity_navi")
		private Integer velocityNavi;

		/**
		 * 信处的相对时间（精确到1ms），由PS侧计时
		 */
		@JSONField(name = "sig_proc_relative_time")
		private Long sigProcRelativeTime;

	}

}
