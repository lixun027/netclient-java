/*
 * Created by 李迅 on 2023-05-22 9:17...
 */
package com.tak.socket.trans.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-22 9:17
 */
@Getter
@AllArgsConstructor
public enum C2NetTypeEnum {

	TCP,

	UDP,

	WS,

	WS_DRONE_ID,

	WS_RADAR_AIRPLANE,

	WS_POSTURE,

	;

}
