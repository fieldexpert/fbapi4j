package com.fieldexpert.fbapi4j;

import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;

abstract class AbstractHandler<T extends Entity> implements Handler<T> {
	protected Dispatch dispatch;
	protected Util util;
	protected String token;

	protected AbstractHandler(Dispatch dispatch, Util util, String token) {
		this.dispatch = dispatch;
		this.util = util;
		this.token = token;
	}

}
