package com.fieldexpert.fbapi4j;

import java.util.HashMap;
import java.util.Map;

public class Priority extends Entity {

	private Map<String, Object> fields = new HashMap<String, Object>();

	Priority(Long id, String name) {
		fields.put(Fbapi4j.IX_PRIORITY, id);
		fields.put(Fbapi4j.S_PRIORITY, name);
	}

	public Long getId() {
		return (Long) fields.get(Fbapi4j.IX_PRIORITY);
	}

	public String getName() {
		return (String) fields.get(Fbapi4j.S_PRIORITY);
	}

}
