package com.fieldexpert.fbapi4j;

import com.fieldexpert.fbapi4j.dispatch.Dispatch;

class SessionFactory {

	Session createSession(Configuration configuration) {
		return new MuxSession(configuration.buildDispatch());
	}

	Session createSession(Dispatch dispatch) {
		return new MuxSession(dispatch);
	}

	private static final ThreadLocal<Session> currentSession = new ThreadLocal<Session>();

	static void setCurrentSession(Session session) {
		currentSession.set(session);
	}

	static Session getCurrentSession() {
		Session session = currentSession.get();
		if (session == null) {
			throw new Fbapi4jException("No session available");
		}
		return session;
	}
}
