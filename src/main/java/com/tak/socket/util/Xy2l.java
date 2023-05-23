/*
 * Created by 李迅 on 2023-05-22 20:36...
 */
package com.tak.socket.util;

import com.tak.socket.trans.model.Place;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-22 20:36
 */
public class Xy2l {

	public static Place trans(Double lon, Double lat, Double x, Double y) {
		GeodesicData result = Geodesic.WGS84.Direct(lat, lon, 90.0, x * 1000);
		double longitude = result.lon2;

		result = Geodesic.WGS84.Direct(lat, lon, 0.0, y * 1000);
		double latitude = result.lat2;

		return new Place(longitude, latitude);
	}

}
