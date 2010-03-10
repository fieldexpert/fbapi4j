package com.fieldexpert.fbapi4j;

import java.util.HashMap;
import java.util.Map;

abstract class Entity {

	protected Map<String, Object> fields = new HashMap<String, Object>();

	abstract Integer getId();

}
