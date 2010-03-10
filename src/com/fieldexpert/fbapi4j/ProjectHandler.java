package com.fieldexpert.fbapi4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;
import com.fieldexpert.fbapi4j.dispatch.Request;
import com.fieldexpert.fbapi4j.dispatch.Response;

class ProjectHandler extends AbstractHandler<Project> {

	ProjectHandler(Dispatch dispatch, Util util, String token) {
		super(dispatch, util, token);
	}

	public List<Project> findAll() {
		Response resp = dispatch.invoke(new Request(Fbapi4j.LIST_PROJECTS, util.map(Fbapi4j.TOKEN, token)));
		List<Map<String, String>> list = util.data(resp.getDocument(), "project");
		List<Project> projects = new ArrayList<Project>();
		for (Map<String, String> map : list) {
			projects.add(new Project(Integer.parseInt(map.get(Fbapi4j.IX_PROJECT)), map.get(Fbapi4j.S_PROJECT)));
		}
		return projects;
	}

}
