package com.fieldexpert.fbapi4j.common;

import static junit.framework.Assert.*;

import java.io.FileInputStream;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import com.fieldexpert.fbapi4j.common.Util;

public class UtilTest {
	private Util util;

	@Test(expected = IllegalArgumentException.class)
	public void nullMap() {
		String args = null;
		util.map(args);
	}

	@Test(expected = IllegalArgumentException.class)
	public void oddArgsMap() {
		util.map("foo", "bar", "baz");
	}

	@Test
	public void map() {
		Map<String, Object> map = util.map("foo", "bar", "baz", "quux");

		assertEquals(2, map.size());

		assertEquals("bar", map.get("foo"));
		assertEquals("quux", map.get("baz"));
	}

	@Test
	public void children() throws Exception {
		Document doc = util.document(new FileInputStream("test/fbapi4j.xml"));
		Map<String, String> map = util.children(doc);

		assertNotNull(map);
		assertEquals(1, map.size());
		assertTrue(map.containsKey("api"));
	}

	@Test
	public void document() throws Exception {
		Document doc = util.document(new FileInputStream("test/fbapi4j.xml"));
		assertNotNull(doc);
		assertEquals("fbapi4j", doc.getDocumentElement().getNodeName());
	}

	@Before
	public void setup() {
		this.util = new Util();
	}
}
