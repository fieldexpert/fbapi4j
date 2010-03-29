package com.fieldexpert.fbapi4j.demo;

import com.fieldexpert.fbapi4j.Configuration;
import com.fieldexpert.fbapi4j.Priority;
import com.fieldexpert.fbapi4j.Session;

public class PriorityDemo {

	public static void main(String[] args) {
		Configuration conf = new Configuration().configure();
		Session session = conf.buildSession();

		for (Priority priority : session.findAll(Priority.class)) {
			System.out.println(priority.getId() + " -> " + priority.getName());
		}

		Priority priority = session.get(Priority.class, 7);
		System.out.println(priority.getName());

		System.out.println(session.get(Priority.class, ""));
		session.close();
	}

}
