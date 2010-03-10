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

		Project project = session.get(Project.class, 1);
		System.out.println(project.getName());
		session.close();
	}

}
