/*
 * Created by 李迅 on 2023-05-17 16:27...
 */
package com.tak.socket.util;

import org.joda.time.format.DateTimeFormat;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-17 16:27
 */
public class DateUtil {

	public interface Format {

		String DATE_TIME_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

		String DATE_TIME_FORMAT2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	}

	/**
	 * 获取当前时间
	 */
	public static String currentTimeFormat(String format) {
		return DateTimeFormat.forPattern(format).withOffsetParsed().print(System.currentTimeMillis());
	}

	/**
	 * 获取指定时间指定格式
	 */
	public static String dateTimeFormat(String format, Long timeMillis) {
		return DateTimeFormat.forPattern(format).withOffsetParsed().print(timeMillis);
	}

	/**
	 * 在指定的时间上加上指定的天数
	 * 当前时间：2023-05-18T16:15:05.344Z
	 * 加7天后 ：2023-05-25T16:15:05.344Z
	 */
	public static String addDays(String format, String text, int days) {
		return DateTimeFormat.forPattern(format).parseLocalDateTime(text).plusDays(days).toString(format);
	}

	public static String addMinutes(String format, String text, int minutes) {
		return DateTimeFormat.forPattern(format).parseLocalDateTime(text).plusMinutes(minutes).toString(format);
	}

}
