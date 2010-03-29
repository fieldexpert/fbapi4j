package com.fieldexpert.fbapi4j.demo;

import com.fieldexpert.fbapi4j.Area;
import com.fieldexpert.fbapi4j.Configuration;
import com.fieldexpert.fbapi4j.Project;
import com.fieldexpert.fbapi4j.Session;

public class AreaDemo {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration().configure();
		Session session = conf.buildSession();

		for (Area area : session.findAll(Area.class)) {
			System.out.println(area.getId() + " -> " + area.getName() + " -> " + area.getProject().getName());
		}

		Project project = session.get(Project.class, 8); // 8 = FX Internal
		Area area = new Area("Nathan's Area 7", project);
		session.create(area);
		System.out.println(area.getId());
		session.close();

	}

}
