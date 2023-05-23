package com.tak.socket.trans.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Place {

	private Double x;

	private Double y;

	public Place(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
