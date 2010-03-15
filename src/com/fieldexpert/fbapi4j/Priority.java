package com.fieldexpert.fbapi4j;

@EntityConfig(element = "priority", list = Fbapi4j.LIST_PRIORITIES, single = Fbapi4j.VIEW_PRIORITY, id = Fbapi4j.IX_PRIORITY, name = Fbapi4j.S_PRIORITY)
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
