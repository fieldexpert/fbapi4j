package com.fieldexpert.fbapi4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;
import com.fieldexpert.fbapi4j.dispatch.Request;
import com.fieldexpert.fbapi4j.dispatch.Response;

class PriorityHandler extends AbstractHandler<Priority> {

	PriorityHandler(Dispatch dispatch, Util util, String token) {
		super(dispatch, util, token);
	}

	public void create(Priority p) {
		throw new UnsupportedOperationException("Operation is not *currently* supported by fbapi4j.");
	}

	public List<Priority> findAll() {
		Response resp = dispatch.invoke(new Request(Fbapi4j.LIST_PRIORITIES, util.map(Fbapi4j.TOKEN, token)));
		List<Map<String, String>> list = util.data(resp.getDocument(), "priority");
		List<Priority> priorities = new ArrayList<Priority>();
		for (Map<String, String> map : list) {
			priorities.add(new Priority(Integer.parseInt(map.get(Fbapi4j.IX_PRIORITY)), map.get(Fbapi4j.S_PRIORITY)));
		}
		return priorities;
	}

	public Priority findById(Integer id) {
		Response resp = dispatch.invoke(new Request(Fbapi4j.VIEW_PRIORITY, util.map(Fbapi4j.TOKEN, token, Fbapi4j.IX_PRIORITY, id)));
		Map<String, String> map = util.data(resp.getDocument(), "priority").get(0);
		return new Priority(Integer.parseInt(map.get(Fbapi4j.IX_PRIORITY)), map.get(Fbapi4j.S_PRIORITY));
	}

}
