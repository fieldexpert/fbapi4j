package com.fieldexpert.fbapi4j;

import java.io.File;
import java.util.Date;

import com.fieldexpert.fbapi4j.Case;
import com.fieldexpert.fbapi4j.Configuration;
import com.fieldexpert.fbapi4j.Priority;
import com.fieldexpert.fbapi4j.Session;

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
		session.scout(bug1);
		
		//Case bug2 = new Case("Internal Field Expert", "Misc", "Brand new case", "My description");
		//session.create(bug2);
		//session.resolve(bug2);
		//session.close(bug2);


		Case c = session.get(Case.class, 1190L);
		//session.reopen(c);
		//session.close(c);
		//session.resolve(c);
		//session.reactivate(c);
		c.setDescription("Some new description 3");
		c.setTags("Tag 1", "Tag 3");
		c.setHoursEstimate(2);
		c.setPriority(Priority.FIX_IF_TIME_5);
		Case parent = session.get(Case.class, 1191L);
		c.setParent(parent);
		c.setDueDate(new Date());
		c.setArea("Area 3");
		c.setAssignedTo("Nathan Bowser");
		//session.edit(c);
		session.assign(c);
		session.close();
	}

}
