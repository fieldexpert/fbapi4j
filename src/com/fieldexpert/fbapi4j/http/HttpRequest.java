package com.fieldexpert.fbapi4j.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

abstract class HttpRequest {

	private static final String CONTENT_TYPE = "Content-Type";

	protected static String buildQueryString(Map<String, String> parameters) {
		if (parameters == null || parameters.isEmpty()) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (Entry<String, String> e : parameters.entrySet()) {
			builder.append(escape(e.getKey()));
			builder.append("=");
			builder.append(escape(e.getValue()));
			builder.append("&");
		}
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

	private static String escape(String text) {
		try {
			return URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Cannot find UTF-8 encoding");
		}
	}

	protected String method;
	protected URL url;
	protected Map<String, String> parameters;

	public HttpRequest(String method, URL url, Map<String, String> parameters) {
		this.method = method;
		this.url = url;
		this.parameters = parameters;
	}

	protected HttpURLConnection conn(URL url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setAllowUserInteraction(false);
		conn.setRequestMethod(getMethod());
		return conn;
	}

	protected String getMethod() {
		return method;
	}

	abstract boolean requiresWrite();

	abstract String getContentType();

	abstract void write(OutputStream out) throws IOException;

	final InputStream execute() throws IOException {
		HttpURLConnection conn = conn(url);

		String type = getContentType();
		if (type != null) {
			conn.setRequestProperty(CONTENT_TYPE, type);
		}

		conn.connect();

		if (requiresWrite()) {
			OutputStream out = conn.getOutputStream();
			write(out);
			out.close();
		}

		return conn.getInputStream();
	}
}
