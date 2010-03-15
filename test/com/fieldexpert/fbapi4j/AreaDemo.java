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

		Project project = session.get(Project.class, 8); // 8 = FX Internal
		Area area = new Area("Nathan's Area 7", project);
		System.out.println(area.getId());
		session.create(area);
		session.close();

	}

}
