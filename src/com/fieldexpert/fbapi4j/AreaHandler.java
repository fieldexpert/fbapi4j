package com.fieldexpert.fbapi4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;
import com.fieldexpert.fbapi4j.dispatch.Request;
import com.fieldexpert.fbapi4j.dispatch.Response;

class AreaHandler extends AbstractHandler<Area> {

	AreaHandler(Dispatch dispatch, Util util, String token) {
		super(dispatch, util, token);
	}

	public List<Area> findAll() {
		Response resp = dispatch.invoke(new Request(Fbapi4j.LIST_AREAS, util.map(Fbapi4j.TOKEN, token)));
		List<Map<String, String>> list = util.data(resp.getDocument(), "area");
		List<Area> areas = new ArrayList<Area>();
		for (Map<String, String> map : list) {
			Integer owner = null;
			if (map.containsKey(Fbapi4j.IX_PERSON_OWNER)) {
				owner = Integer.parseInt(map.get(Fbapi4j.IX_PERSON_OWNER));
			}
			areas.add(new Area(Integer.parseInt(map.get(Fbapi4j.IX_AREA)), map.get(Fbapi4j.S_AREA), owner, Integer.parseInt(map.get(Fbapi4j.IX_PROJECT))));
		}
		return areas;
	}
}
