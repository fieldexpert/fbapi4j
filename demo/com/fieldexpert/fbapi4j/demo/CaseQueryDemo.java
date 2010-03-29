package com.fieldexpert.fbapi4j.demo;

import java.util.List;

import com.fieldexpert.fbapi4j.Case;
import com.fieldexpert.fbapi4j.Configuration;
import com.fieldexpert.fbapi4j.Event;
import com.fieldexpert.fbapi4j.Session;
import com.fieldexpert.fbapi4j.common.Attachment;

public class CaseQueryDemo {

	public static void main(String[] args) {
		Configuration conf = new Configuration().configure();
		Session session = conf.buildSession();

		List<Case> cases = session.query("1144", "1145");
		for (Case c : cases) {
			System.out.println(c.getTitle());
			for (Event event : c.getEvents()) {
				System.out.println(event.getDescription());
				for (Attachment attachment : event.getAttachments()) {
					System.out.println(attachment.getFilename());
					//System.out.println(attachment.getContent());
				}
			}
			System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		}
		session.close();
	}

}
