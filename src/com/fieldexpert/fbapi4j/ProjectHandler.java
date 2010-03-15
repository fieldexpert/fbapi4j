package com.fieldexpert.fbapi4j;

import java.util.Map;

import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;

@EntityConfig(element = "project", list = Fbapi4j.LIST_PROJECTS, single = Fbapi4j.VIEW_PROJECT, id = Fbapi4j.IX_PROJECT, name = Fbapi4j.S_PROJECT)
class ProjectHandler extends AbstractHandler<Project> {

	ProjectHandler(Dispatch dispatch, Util util, String token) {
		super(dispatch, util, token);
	}

	public void create(Project p) {
		// TODO Implement
		throw new UnsupportedOperationException("Operation is not *currently* supported by fbapi4j.");
	}

	@Override
	Project build(Map<String, String> data) {
		return new Project(Integer.parseInt(data.get(Fbapi4j.IX_PROJECT)), data.get(Fbapi4j.S_PROJECT));
	}

}
