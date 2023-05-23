/*
 * Created by 李迅 on 2023-05-19 11:36...
 */
package com.tak.socket.trans.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-19 11:36
 */
@Getter
@AllArgsConstructor
public enum CotHeightUnitEnum {


	KM(0, "kilometers"),

	M(1, "meters"),

	MI(2, "miles"),

	YD(3, "yards"),

	FT(4, "feet"),

	NM(5, "nautical miles"),

	;

	private final Integer iUnit;

	private final String sUnit;

}
