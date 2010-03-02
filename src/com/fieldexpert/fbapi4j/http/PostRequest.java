package com.fieldexpert.fbapi4j.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

class PostRequest extends HttpRequest {

	private static final String FORM_URLENCODED = "application/x-www-form-urlencoded;charset=UTF-8";

	public PostRequest(URL url, Map<String, String> parameters) {
		super(Http.POST, url, parameters);
	}

	@Override
	String getContentType() {
		if (parameters != null && !parameters.isEmpty()) {
			return FORM_URLENCODED;
		} else {
			return null;
		}
	}

	@Override
	void write(OutputStream out) throws IOException {
		DataOutputStream data = new DataOutputStream(out);
		data.writeBytes(buildQueryString(parameters));
		data.flush();
	}
}
