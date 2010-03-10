package com.fieldexpert.fbapi4j;

import java.util.HashMap;
import java.util.Map;

public class Person extends Entity {

	private Map<String, Object> fields = new HashMap<String, Object>();

	Person(Integer id, String email, String fullname, String phone) {
		fields.put(Fbapi4j.IX_PERSON, id);
		fields.put(Fbapi4j.S_EMAIL, email);
		fields.put(Fbapi4j.S_FULLNAME, fullname);
		fields.put(Fbapi4j.S_PHONE, phone);
	}

	public String getEmail() {
		return (String) fields.get(Fbapi4j.S_EMAIL);
	}

	public String getFullname() {
		return (String) fields.get(Fbapi4j.S_FULLNAME);
	}

	public Integer getId() {
		return (Integer) fields.get(Fbapi4j.IX_PERSON);
	}

	public String getPhone() {
		return (String) fields.get(Fbapi4j.S_PHONE);
	}

}
