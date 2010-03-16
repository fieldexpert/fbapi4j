package com.fieldexpert.fbapi4j.common;

import org.junit.Test;

public class AssertTest {

	@Test(expected = IllegalArgumentException.class)
	public void nullObj() {
		Assert.notNull(null);
	}

	@Test
	public void notNullObj() {
		Assert.notNull(new Object());
	}

}
