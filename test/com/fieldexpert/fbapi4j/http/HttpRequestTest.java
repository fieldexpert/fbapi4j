package com.fieldexpert.fbapi4j.http;

import static junit.framework.Assert.assertEquals;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

public class HttpRequestTest {

	@Test
	public void nullQueryString() {
		assertEquals("", HttpRequest.buildQueryString(null));
	}

	@Test
	public void emptyQueryString() {
		Map<String, String> empty = Collections.emptyMap();
		assertEquals("", HttpRequest.buildQueryString(empty));
	}

	@Test
	public void buildQueryString() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("foo", "bar");
		map.put("baz", "bizz");

		String query = "foo=bar&baz=bizz";
		assertEquals(query, HttpRequest.buildQueryString(map));
	}

	@Test
	public void buildEncodedString() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("foo", "bar bizz buck");
		map.put("rate", "15%");

		String query = "foo=bar+bizz+buck&rate=15%25";
		assertEquals(query, HttpRequest.buildQueryString(map));
	}

}
