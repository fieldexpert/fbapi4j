package com.fieldexpert.fbapi4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;
import com.fieldexpert.fbapi4j.dispatch.Request;
import com.fieldexpert.fbapi4j.dispatch.Response;

public class AreaHandler implements Handler<Area> {
	private Dispatch dispatch;
	private Util util;
	private String token;

	public AreaHandler(Dispatch dispatch, Util util, String token) {
		this.dispatch = dispatch;
		this.util = util;
		this.token = token;
	}

	public void create(Area t) {
		throw new UnsupportedOperationException("Operation is not *currently* supported by fbapi4j.");
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

	public Area findById(Integer id) {
		for (Area area : findAll()) {
			if (area.getId() == id) {
				return area;
			}
		}
		return null;
	}
}
