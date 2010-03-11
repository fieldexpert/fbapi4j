package com.fieldexpert.fbapi4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;
import com.fieldexpert.fbapi4j.dispatch.Request;
import com.fieldexpert.fbapi4j.dispatch.Response;

class AreaHandler extends AbstractHandler<Area> {

	AreaHandler(Dispatch dispatch, Util util, String token) {
		super(dispatch, util, token);
	}

	public void create(Area area) {
		Map<String, Object> params = new HashMap<String, Object>(area.getFields());
		params.put(Fbapi4j.TOKEN, token);
		Response resp = dispatch.invoke(new Request(Fbapi4j.NEW_AREA, params));
		Node node = resp.getDocument().getElementsByTagName("area").item(0);
		Integer id = Integer.parseInt(util.children(node).get(Fbapi4j.IX_AREA));
		area.setId(id);
	}

	public List<Area> findAll() {
		Response resp = dispatch.invoke(new Request(Fbapi4j.LIST_AREAS, util.map(Fbapi4j.TOKEN, token)));
		return buildAreas(resp);
	}

	private List<Area> buildAreas(Response resp) {
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

	public List<Area> getByProject(Project project) {
		return getByProject(project.getId());
	}

	public List<Area> getByProject(Integer project) {
		Response resp = dispatch.invoke(new Request(Fbapi4j.LIST_AREAS, util.map(Fbapi4j.TOKEN, token, Fbapi4j.IX_PROJECT, project)));
		return buildAreas(resp);
	}

}
