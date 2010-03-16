package com.fieldexpert.fbapi4j.common;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

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
	public void merge() {
		Map<String, String> map1 = new HashMap<String, String>();
		Map<String, String> map2 = new HashMap<String, String>();

		map1.put("foo", "apple");
		map1.put("bar", "banana");
		map2.put("miracle fruit", "Synsepalum dulcificum");

		Map<String, String> merged = util.merge(map1, map2);
		assertEquals(3, merged.size());
		assertTrue(merged.containsKey("foo"));
		assertTrue(merged.containsValue("banana"));
	}

	@Test
	public void attributes() throws Exception {
		Document doc = util.document(new FileInputStream("test/fbapi4j.xml"));
		Node root = doc.getDocumentElement();

		Map<String, String> map = util.attributes(root);

		assertNotNull(map);
		assertTrue(map.containsKey("date"));
		assertEquals("now", map.get("date"));
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
	public void data() throws Exception {
		Document doc = util.document(new FileInputStream("test/fbapi4j.xml"));
		List<Map<String, String>> data = util.data(doc, "fbapi4j");

		assertEquals(1, data.size());
		Map<String, String> map = data.get(0);
		assertTrue(map.containsKey("api"));
		assertEquals("now", map.get("date"));
	}

	@Test
	public void string() throws Exception {
		FileInputStream file = new FileInputStream("test/fbapi4j.xml");
		Document doc = util.document(file);
		String text = util.string(doc);
		assertNotNull(text);
	}

	@Test
	public void document() throws Exception {
		Document doc = util.document(new FileInputStream("test/fbapi4j.xml"));
		assertNotNull(doc);
		assertEquals("fbapi4j", doc.getDocumentElement().getNodeName());
	}

	@Before
	public void setup() throws Exception {
		this.util = new Util();
	}
}
