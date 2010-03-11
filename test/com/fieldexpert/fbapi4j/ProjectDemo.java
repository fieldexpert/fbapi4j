package com.fieldexpert.fbapi4j;

public class ProjectDemo {

	public static void main(String[] args) throws Exception {
		String email = args[0];
		String password = args[1];

		Configuration conf = new Configuration();
		conf.setProperty("endpoint", "https://fieldexpert.fogbugz.com/");
		conf.setProperty("email", email);
		conf.setProperty("password", password);

		Session session = conf.getSession();

		//session.create(new Project("Nathan Test Project"));

		for (Project project : session.findAll(Project.class)) {
			System.out.println(project.getId() + " -> " + project.getName());
		}

		Project project = session.get(Project.class, 2);
		System.out.println(project.getName());
		System.out.println(project.getCases().size());
		System.out.println(project.getAreas());
		session.close();

		// Throws an exception since there isn't a session available.
		System.out.println(project.getCases());
	}

}
