package com.fieldexpert.fbapi4j;


public class SubmitterDemo {

	public static void main(String[] args) throws Exception {
		String email = args[0];
		String password = args[1];

		Configuration conf = new Configuration();
		conf.setProperty("endpoint", "https://fieldexpert.fogbugz.com/");
		conf.setProperty("email", email);
		conf.setProperty("password", password);

		Case bug1 = new Case("Internal Field Expert", "Misc", "Test Case Title", "Case Event 1").attach("build.xml", "text/xml", "My Custom Build file!");

		Submitter submitter = new Submitter(conf);
		submitter.logon();
		submitter.submit(bug1);
		// submitter.submit(bug2);
		submitter.logoff();
	}
}
