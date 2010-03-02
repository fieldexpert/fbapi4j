package com.fieldexpert.fbapi4j;

public class SearchDemo {

	public static void main(String[] args) throws Exception {
		String email = args[0];
		String password = args[1];

		Configuration conf = new Configuration();
		conf.setProperty("endpoint", "https://fieldexpert.fogbugz.com/");
		conf.setProperty("email", email);
		conf.setProperty("password", password);

		Submitter submitter = new Submitter(conf);
		submitter.logon();

		for (Case c : submitter.cases("proclaim")) {
			System.out.println(c);
		}

		submitter.logoff();
	}
}
