/*
 * Created by 李迅 on 2023-05-22 20:49...
 */
package com.tak.socket.test;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-22 20:49
 */
public class Test {


	public static void offset(Double latitude, Double longitude, Double distanceInMeter, Double bearing) {

		Double equatorRadius = 6378137.0;
		double h = degToRadian(bearing);

		double a = distanceInMeter / equatorRadius;

		Double lat2 = Math.asin(Math.sin(degToRadian(latitude)) * Math.cos(a) +
				Math.cos(degToRadian(latitude) * Math.sin(a) * Math.cos(h)));

		Double lng2 = degToRadian(longitude) +
				Math.atan2(Math.sin(h) * Math.sin(a) * Math.cos(degToRadian(latitude)),
						Math.cos(a) - Math.sin(degToRadian(latitude)) * Math.sin(lat2));
		System.err.println(lat2);
		System.err.println(lng2);

	}

	// Converts degree to radian
	static double degToRadian(final double deg) {
		return deg * (Math.PI / 180.0);
	}

	/// Radian to degree
	double radianToDeg(final double rad) {
		return rad * (180.0 / Math.PI);
	}

	public static void main(String[] args) {

		Double x = -202D;
		Double y = 223D;


		double sqrt = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

		// 根据 xy 计算与y轴的夹角度数
		double a = Math.atan2(x, y);
		double angle = degToRadian(a);


		offset(22.60003662109375D, 114.00656127929688D, sqrt, angle);
	}

}
