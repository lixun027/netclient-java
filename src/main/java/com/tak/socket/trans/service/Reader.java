package com.tak.socket.trans.service;

import com.tak.socket.trans.enums.C2NetTypeEnum;

public interface Reader {
	void read(String msg, C2NetTypeEnum type);
}