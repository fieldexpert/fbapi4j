package com.fieldexpert.fbapi4j;

public class AreaDemo {

	public static void main(String[] args) throws Exception {
		String email = args[0];
		String password = args[1];

		Configuration conf = new Configuration();
		conf.setProperty("endpoint", "https://fieldexpert.fogbugz.com/");
		conf.setProperty("email", email);
		conf.setProperty("password", password);

		Session session = conf.getSession();

		for (Area area : session.findAll(Area.class)) {
			System.out.println(area.getId() + " -> " + area.getName() + " -> " + area.getProject().getName());
		}

		session.close();

	}

}
