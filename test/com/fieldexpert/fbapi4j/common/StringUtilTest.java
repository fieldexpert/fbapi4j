package com.fieldexpert.fbapi4j.common;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fieldexpert.fbapi4j.common.StringUtil;

public class StringUtilTest {
	private List<String> list;

	@Test
	public void match() {
		assertEquals("foo,bar,baz,quux", StringUtil.collectionToCommaDelimitedString(list));
	}

	@Test
	public void matchString() {
		assertEquals("foo~bar~baz~quux", StringUtil.collectionToDelimitedString(list, "~"));
	}

	@Test
	public void empty() {
		assertEquals("", StringUtil.collectionToCommaDelimitedString(new ArrayList<String>()));
	}

	@Test
	public void nullTest() {
		assertEquals("", StringUtil.collectionToCommaDelimitedString(null));
	}

	@Before
	public void setup() {
		list = Arrays.asList("foo", "bar", "baz", "quux");
	}

}
