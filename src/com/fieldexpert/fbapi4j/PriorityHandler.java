package com.fieldexpert.fbapi4j;

import java.util.Map;

import org.w3c.dom.Document;

import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;

class PriorityHandler extends AbstractHandler<Priority> {

	PriorityHandler(Dispatch dispatch, Util util, String token) {
		super(dispatch, util, token);
	}

	@Override
	Priority build(Map<String, String> data, Document doc) {
		return new Priority(Integer.parseInt(data.get(Fbapi4j.IX_PRIORITY)), data.get(Fbapi4j.S_PRIORITY));
	}

	public void create(Priority p) {
		throw new UnsupportedOperationException("Operation is not supported by the Fogbugz API");
	}

	@Override
	public Priority findByName(String name) {
		throw new Fbapi4jException("Operation is not supported by fogbugz");
	}

}
