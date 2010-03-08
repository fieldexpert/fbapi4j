package com.fieldexpert.fbapi4j.session;

import java.io.File;

import com.fieldexpert.fbapi4j.Case;
import com.fieldexpert.fbapi4j.Configuration;

public class SessionDemo {

	public static void main(String[] args) throws Exception {
		String email = args[0];
		String password = args[1];

		Configuration conf = new Configuration();
		conf.setProperty("endpoint", "https://fieldexpert.fogbugz.com/");
		conf.setProperty("email", email);
		conf.setProperty("password", password);

		Session session = conf.getSession();
		Case bug1 = new Case("Internal Field Expert", "Misc", "Test Case Title", "Case Event 20").attach("build.xml", "text/xml", "My Custom Build file!").attach(new File("test/fbapi4j.xml"));
		session.create(bug1);
		session.close();
	}

}
