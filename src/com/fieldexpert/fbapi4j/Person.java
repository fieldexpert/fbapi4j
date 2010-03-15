package com.fieldexpert.fbapi4j;

@EntityConfig(element = "person", list = Fbapi4j.LIST_PEOPLE, single = Fbapi4j.VIEW_PERSON, id = Fbapi4j.IX_PERSON, name = Fbapi4j.S_PERSON)
public class Person extends Entity {

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
