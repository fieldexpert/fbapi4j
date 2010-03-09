package com.fieldexpert.fbapi4j;

import com.fieldexpert.fbapi4j.dispatch.Dispatch;

class SessionFactory {

	Session createSession(Configuration configuration) {
		return new MuxSession(configuration.buildDispatch());
	}

	Session createSession(Dispatch dispatch) {
		return new MuxSession(dispatch);
	}

}
