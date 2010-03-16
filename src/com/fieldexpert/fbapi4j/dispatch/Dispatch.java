package com.fieldexpert.fbapi4j.dispatch;

import java.net.URL;
import java.util.Properties;

public interface Dispatch {

	Response invoke(String method, URL url);

	Response invoke(Request request);

	Response invoke(String method, URL url, Request request);

	URL getEndpoint();

	URL getUrl();

	Properties getProperties();

	String getProperty(String name);

	void setProperty(String name, String value);

	String getEmail();

	String getPassword();

}
