package com.fieldexpert.fbapi4j.demo;

import java.io.File;
import java.util.Date;

import com.fieldexpert.fbapi4j.Case;
import com.fieldexpert.fbapi4j.Configuration;
import com.fieldexpert.fbapi4j.Session;

public class CaseDemo {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration().configure();
		Session session = conf.buildSession();

		Case bug1 = new Case("Internal Field Expert", "Misc", "Test Case Title", "Case Event 20").attach("build.xml", "text/xml", "My Custom Build file!").attach(new File("test/fbapi4j.xml"));
		session.scout(bug1);

		//Case bug2 = new Case("Internal Field Expert", "Misc", "Brand new case", "My description");
		//session.create(bug2);
		//session.resolve(bug2);
		//session.close(bug2);

		Case c = session.get(Case.class, 1190);
		//session.reopen(c);
		//session.close(c);
		//session.resolve(c);
		//session.reactivate(c);
		c.setDescription("Some new description 3");
		c.setTags("Tag 1", "Tag 3");
		c.setHoursEstimate(2);
		c.setPriority(7);
		Case parent = session.get(Case.class, 1191);
		c.setParent(parent);
		c.setDueDate(new Date());
		c.setArea("Area 3");
		c.attach(new File("test/com/fieldexpert/fbapi4j/CaseTest.java"));
		c.setAssignedTo("Nathan Bowser");
		session.edit(c);
		session.close();
	}

}
