package com.fieldexpert.fbapi4j.dispatch;

import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fieldexpert.fbapi4j.Configuration;
import com.fieldexpert.fbapi4j.http.Http;

public final class Dispatch {

	private Configuration configuration;
	private DocumentBuilder builder;

	public Dispatch(Configuration configuration) {
		this.configuration = new Configuration(configuration);

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (Exception e) {
			throw new DispatchException(e);
		}
	}

	public Response invoke(String method, URL url) {
		return invoke(method, url, null);
	}

	public Response invoke(Request request) {
		return invoke(Http.POST, configuration.getUrl(), request);
	}

	public Response invoke(String method, URL url, Request request) {
		Map<String, String> parameters;
		if (request != null && !request.isEmpty()) {
			parameters = new HashMap<String, String>(request.size());
			for (Entry<String, Object> e : request.entrySet()) {
				String key = e.getKey();
				Object value = e.getValue();
				parameters.put(key, value == null ? "null" : value.toString());
			}
		} else {
			parameters = Collections.emptyMap();
		}

		InputStream resp = null;
		if (request != null && request.getAttachments().size() > 0) {
			resp = Http.req(method, url, parameters, request.getAttachments());
		} else {
			resp = Http.req(method, url, parameters);
		}

		// for now, we only handle errors from the actual document,
		// in the future, we should refactor a bit so that we can
		// include errors from http as well
		Document document = document(resp);

		// possibly throw an exception
		error(document);

		return new Response(document);
	}

	public URL getEndpoint() {
		return configuration.getEndpoint();
	}

	public URL getUrl() {
		return configuration.getUrl();
	}

	public Properties getProperties() {
		return configuration.getProperties();
	}

	public String getProperty(String name) {
		return configuration.getProperty(name);
	}

	public void setProperty(String name, String value) {
		configuration.setProperty(name, value);
	}

	private Document document(InputStream input) {
		Document document = null;
		try {
			document = builder.parse(input);
		} catch (Exception e) {
			throw new DispatchException(e);
		}

		return document;
	}

	public String getEmail() {
		return configuration.getEmail();
	}

	public String getPassword() {
		return configuration.getPassword();
	}

	private void error(Document document) {
		try {
			Element response = document.getDocumentElement();
			NodeList errors = response.getElementsByTagName("error");
			if (errors.getLength() > 0) {
				Node error = errors.item(0);
				String code = error.getAttributes().getNamedItem("code").getTextContent();
				String message = error.getTextContent();
				throw new DispatchException(code, message);
			}
		} catch (Exception e) {
			throw new DispatchException(e);
		}
	}
}
