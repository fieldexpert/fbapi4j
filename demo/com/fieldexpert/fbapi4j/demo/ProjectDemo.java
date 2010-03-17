package com.fieldexpert.fbapi4j.demo;

import com.fieldexpert.fbapi4j.Configuration;
import com.fieldexpert.fbapi4j.Project;
import com.fieldexpert.fbapi4j.Session;

public class ProjectDemo {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration().configure();
		Session session = conf.getSession();

		//session.create(new Project("Nathan Test Project"));

		for (Project project : session.findAll(Project.class)) {
			System.out.println(project.getId() + " -> " + project.getName());
		}

		Project project = session.get(Project.class, 2);
		System.out.println(project.getName());
		System.out.println(project.getCases().size());
		System.out.println(project.getAreas());

		System.out.println(session.get(Project.class, "Comet Circle").getId());
		session.close();

		// Throws an exception since there isn't a session available.
		System.out.println(project.getCases());
	}

}
