package com.fieldexpert.fbapi4j.demo;

import com.fieldexpert.fbapi4j.Configuration;
import com.fieldexpert.fbapi4j.Person;
import com.fieldexpert.fbapi4j.Session;

public class PeopleDemo {

	public static void main(String[] args) {
		Configuration conf = new Configuration().configure();
		Session session = conf.buildSession();

		for (Person p : session.findAll(Person.class)) {
			System.out.println(p.getId() + " -> " + p.getFullname());
		}
		Person person = session.get(Person.class, 5);
		System.out.println(person.getFullname());

		System.out.println(session.get(Person.class, "nathan.bowser@fieldexpert.com").getFullname());

		session.close();
	}

}
