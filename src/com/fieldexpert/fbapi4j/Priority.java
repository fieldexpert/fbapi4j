package com.fieldexpert.fbapi4j;


public class Priority extends Entity {

	Priority(Integer id, String name) {
		fields.put(Fbapi4j.IX_PRIORITY, id);
		fields.put(Fbapi4j.S_PRIORITY, name);
	}

	public Integer getId() {
		return (Integer) fields.get(Fbapi4j.IX_PRIORITY);
	}

	public String getName() {
		return (String) fields.get(Fbapi4j.S_PRIORITY);
	}

}
