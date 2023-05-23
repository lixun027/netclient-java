/*
 * Created by 李迅 on 2023-05-17 16:21...
 */
package com.tak.socket.trans.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-17 16:21
 */
@Getter
@AllArgsConstructor
public enum CotTypeEnum {

	TYPE_1("a-f-A"),

	TYPE_2("a-n-G"),

	TYPE_3("a-f-G-U-C-I"),

	TYPE_4("a-u-A"),

	TYPE_5("a-f-A-skyfend-c2"),

	TYPE_6("u-d-v-m"),

	TYPE_7("a-f-A-skyfend-c2-radar"),

	// 雷达
	TYPE_8("b-m-p-s-p-loc"),

	HOW_1("h-g-i-g-o"),


	;

	private final String type;

}
