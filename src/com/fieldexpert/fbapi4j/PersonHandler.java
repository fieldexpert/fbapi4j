package com.fieldexpert.fbapi4j;

import java.util.Map;

import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;

@EntityConfig(element = "person", list = Fbapi4j.LIST_PEOPLE, single = Fbapi4j.VIEW_PERSON, id = Fbapi4j.IX_PERSON, name = Fbapi4j.S_PERSON)
class PersonHandler extends AbstractHandler<Person> {

	PersonHandler(Dispatch dispatch, Util util, String token) {
		super(dispatch, util, token);
	}

	public void create(Person p) {
		// TODO Implement
		throw new UnsupportedOperationException("Operation is not *currently* supported by fbapi4j.");
	}

	@Override
	Person build(Map<String, String> data) {
		return new Person(Integer.parseInt(data.get(Fbapi4j.IX_PERSON)), data.get(Fbapi4j.S_EMAIL), data.get(Fbapi4j.S_FULLNAME), data.get(Fbapi4j.S_PHONE));
	}
}
