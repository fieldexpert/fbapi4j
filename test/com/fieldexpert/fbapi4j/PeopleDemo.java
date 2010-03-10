package com.fieldexpert.fbapi4j;

public class PeopleDemo {

	public static void main(String[] args) {
		String email = args[0];
		String password = args[1];

		Configuration conf = new Configuration().configure();
		conf.setProperty("endpoint", "https://fieldexpert.fogbugz.com/");
		conf.setProperty("email", email);
		conf.setProperty("password", password);

		Session session = conf.getSession();
		
		
		for (Person p : session.findAll(Person.class)) {
			System.out.println(p.getId() + " -> " + p.getFullname());
		}
		Person person = session.get(Person.class, 5L);
		System.out.println(person.getFullname());
		
		session.close();
	}

}
