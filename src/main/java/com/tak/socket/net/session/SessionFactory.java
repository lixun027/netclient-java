/*
 * Created by 李迅 on 2023-05-15 16:45...
 */
package com.tak.socket.net.session;

import com.tak.socket.net.session.impl.StorageSessionImpl;

/**
 * @author lixx
 * @version 1.0
 * @since 2023-05-15 16:45
 */
public abstract class SessionFactory {

	private static final StorageSession storageSession = new StorageSessionImpl();

	public static StorageSession getStorageSession() {
		return storageSession;
	}

}
