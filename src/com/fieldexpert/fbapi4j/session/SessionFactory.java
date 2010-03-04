package com.fieldexpert.fbapi4j.session;

import com.fieldexpert.fbapi4j.Configuration;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;

public class SessionFactory {

	public Session createSession(Configuration configuration) {
		return new MuxSession(configuration.buildDispatch());
	}

	public Session createSession(Dispatch dispatch) {
		return new MuxSession(dispatch);
	}

}
