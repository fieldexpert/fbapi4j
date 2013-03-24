package com.fieldexpert.fbapi4j;

import static junit.framework.Assert.assertEquals;
import junit.framework.AssertionFailedError;

import org.junit.Test;

public class CaseTest {

	@Test
	public void basic() {
		Case bug = new Case("fbapi4j", "Misc", "Test Case Title", "Blue Smoke");

		assertEquals("fbapi4j", bug.getProject());
		assertEquals("Misc", bug.getArea());
		assertEquals("Test Case Title", bug.getTitle());
	}
	
	@Test
	public void customColumnSetter() {
		Case bug = new Case("fbapi4j", "Misc", "Test Case Title", "Blue Smoke");
		bug.setField("some-field", "some-value");
		
		assertEquals("Custom field getter didn't return value given to setter", "some-value", bug.getField("some-field"));
	}
	
	@Test
	public void customColumnUnsetGetter() {
		Case bug = new Case("fbapi4j", "Misc", "Test Case Title", "Blue Smoke");
		
		boolean caughtException = false;
		try {
			// When we call a getter, without having set the field.....
			bug.getField("some-field");
		} catch (IllegalArgumentException ex) {
			// ... we expect to  get an IAE!
			caughtException = true;
		}
		
		if (! caughtException) {
			throw new AssertionFailedError(String.format(
					"Calling custom column getter on Case should throw %s if column not previously set",
					IllegalArgumentException.class.getSimpleName()));
		}
	}

}
