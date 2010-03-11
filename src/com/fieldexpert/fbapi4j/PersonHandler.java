package com.fieldexpert.fbapi4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;
import com.fieldexpert.fbapi4j.dispatch.Request;
import com.fieldexpert.fbapi4j.dispatch.Response;

class PersonHandler extends AbstractHandler<Person> {

	PersonHandler(Dispatch dispatch, Util util, String token) {
		super(dispatch, util, token);
	}

	public void create(Person p) {
		throw new UnsupportedOperationException("Operation is not *currently* supported by fbapi4j.");
	}

	public List<Person> findAll() {
		Response resp = dispatch.invoke(new Request(Fbapi4j.LIST_PEOPLE, util.map(Fbapi4j.TOKEN, token)));
		List<Map<String, String>> results = util.data(resp.getDocument(), "person");
		List<Person> peeps = new ArrayList<Person>();
		for (Map<String, String> p : results) {
			Person person = new Person(Integer.parseInt(p.get(Fbapi4j.IX_PERSON)), p.get(Fbapi4j.S_EMAIL), p.get(Fbapi4j.S_FULLNAME), p.get(Fbapi4j.S_PHONE));
			peeps.add(person);
		}
		return peeps;
	}
}
