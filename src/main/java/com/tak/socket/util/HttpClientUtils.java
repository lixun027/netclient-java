package com.tak.socket.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * http 工具类
 *
 * @author lixx
 * @version 1.0
 * @since 2021-08-14 14:52
 */
public class HttpClientUtils {

	private static AbstractHttpDelegate delegate = new DefaultHttpKit();

	public static AbstractHttpDelegate getDelegate() {
		return delegate;
	}

	public static void setDelegate(AbstractHttpDelegate delegate) {
		HttpClientUtils.delegate = delegate;
	}

	public static String readData(HttpServletRequest request) {
		BufferedReader br = null;
		try {
			StringBuilder result = new StringBuilder();
			br = request.getReader();
			for (String line; (line = br.readLine()) != null; ) {
				if (result.length() > 0) {
					result.append("\n");
				}
				result.append(line);
			}
			return result.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

class DefaultHttpKit extends AbstractHttpDelegate {
}