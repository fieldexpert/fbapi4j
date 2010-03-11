package com.fieldexpert.fbapi4j;

import java.util.List;
import java.util.Map;

public class Project extends Entity {

	public Project(String name) {
		setName(name);
	}

	Project(Integer id, String name) {
		setId(id);
		setName(name);
	}

	public Integer getId() {
		return (Integer) fields.get(Fbapi4j.IX_PROJECT);
	}

	public String getName() {
		return (String) fields.get(Fbapi4j.S_PROJECT);
	}

	void setId(Integer id) {
		fields.put(Fbapi4j.IX_PROJECT, id);
	}

	public void setName(String name) {
		fields.put(Fbapi4j.S_PROJECT, name);
	}

	Map<String, Object> getFields() {
		return fields;
	}

	public List<Case> getCases() {
		return SessionFactory.getCurrentSession().query(getName());
	}

	public List<Area> getAreas() {
		if (getId() == null) {
			throw new Fbapi4jException("Project has not been persisted.");
		}
		ConnectedSession session = (ConnectedSession) SessionFactory.getCurrentSession();
		return session.findAreasByProjectId(getId());
	}

}
