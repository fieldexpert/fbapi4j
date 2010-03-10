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

	public List<Priority> findAll() {
		Response resp = dispatch.invoke(new Request(Fbapi4j.LIST_PRIORITIES, util.map(Fbapi4j.TOKEN, token)));
		List<Map<String, String>> list = util.data(resp.getDocument(), "priority");
		List<Priority> priorities = new ArrayList<Priority>();
		for (Map<String, String> map : list) {
			priorities.add(new Priority(Integer.parseInt(map.get(Fbapi4j.IX_PRIORITY)), map.get(Fbapi4j.S_PRIORITY)));
		}
		return priorities;
	}

}
