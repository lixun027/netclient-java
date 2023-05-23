package com.tak.socket.util;

/**
 * 描述：经纬度转换
 * Created by cxb on 2018/5/24.
 */
public class LatLngTools {

    public static final double a = 6378245.0;
    public static final double ee = 0.00669342162296594323;
    public static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;


    public boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    private double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0 * Math.PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 地球坐标转换为火星坐标
     * World Geodetic System ==> Mars Geodetic System
     *
     * @param wgLat 地球坐标
     * @param wgLon
     */
    public Lation transform2Mars(double wgLat, double wgLon) {
        Lation lation = new Lation();
        if (outOfChina(wgLat, wgLon)) {
            lation.lat = wgLat;
            lation.lon = wgLon;
            return lation;
        }
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * Math.PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);

        lation.lat = wgLat + dLat;
        lation.lon = wgLon + dLon;
        return lation;
    }

    /**
     * 火星坐标转换为百度坐标
     *
     * @param gg_lat
     * @param gg_lon
     */
    public  Lation bd_encrypt(double gg_lat, double gg_lon) {
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        Lation lation = new Lation();
        lation.lon = z * Math.cos(theta) + 0.0065;
        lation.lat = z * Math.sin(theta) + 0.006;
        return lation;
    }

    /**
     * 百度转火星
     *
     * @param bd_lat
     * @param bd_lon
     */
    public Lation bd_decrypt(double bd_lat, double bd_lon) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        Lation lation = new Lation();
        lation.lon = z * Math.cos(theta);
        lation.lat = z * Math.sin(theta);
        return lation;
    }
    public static class Lation {
        double lon;
        double lat;

		@Override
		public String toString() {
			return "Lation{" +
					"lon=" + lon +
					", lat=" + lat +
					'}';
		}
	}
}