package com.fieldexpert.fbapi4j;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class CaseTest {

	@Test
	public void basic() {
		Case bug = new Case("fbapi4j", "Misc", "Test Case Title", "Blue Smoke");

		assertEquals("fbapi4j", bug.getProject());
		assertEquals("Misc", bug.getArea());
		assertEquals("Test Case Title", bug.getTitle());
	}

}
