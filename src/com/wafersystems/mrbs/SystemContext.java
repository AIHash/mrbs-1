package com.wafersystems.mrbs;

import javax.servlet.http.HttpSession;

/**
 * 暂存Session
 * @author Moon
 *
 */
public class SystemContext {

	private static ThreadLocal<HttpSession> _session = new ThreadLocal<HttpSession>(); 

	public static HttpSession get_session() {
		HttpSession session =(HttpSession)_session.get();
		return session;
	}

	public static void set_session(HttpSession session) {
		_session.set(session);
	}
	
}
