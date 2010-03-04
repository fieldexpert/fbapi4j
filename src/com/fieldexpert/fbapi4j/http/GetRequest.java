package com.fieldexpert.fbapi4j.http;

import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

class GetRequest extends HttpRequest {

	public GetRequest(URL url, Map<String, String> parameters) {
		super(Http.GET, url(url, parameters), parameters);
	}

	private static URL url(URL url, Map<String, String> parameters) {
		try {
			if (parameters != null && !parameters.isEmpty()) {
				String u = url.toString();
				if ('?' != u.charAt(u.length() - 1)) {
					u += '?';
				}
				url = new URL(u + buildQueryString(parameters));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return url;
	}

	@Override
	String getContentType() {
		return null;
	}

	@Override
	void write(OutputStream out) {
		// no-op
	}

	@Override
	boolean requiresWrite() {
		return false;
	}
}
