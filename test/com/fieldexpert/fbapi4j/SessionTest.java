package com.fieldexpert.fbapi4j;

import org.junit.Test;

import com.fieldexpert.fbapi4j.Configuration;
import com.fieldexpert.fbapi4j.Session;

public class SessionTest {

	@Test
	public void neverConnects() {
		Configuration config = new Configuration().configure();
		Session session = config.buildSession();
		session.close();
	}

}
