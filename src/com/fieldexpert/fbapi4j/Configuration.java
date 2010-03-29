package com.fieldexpert.fbapi4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.fieldexpert.fbapi4j.dispatch.Dispatch;
import com.fieldexpert.fbapi4j.dispatch.HttpDispatch;

public final class Configuration {

	private static final String ENDPOINT = "endpoint";
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";
	private static final String PATH = "path";

	private Properties properties;

	public Configuration() {
		properties = new Properties();
	}

	public Configuration(Configuration configuration) {
		this.properties = configuration.getProperties();
	}

	public Session getSession() {
		return new SessionFactory().createSession(this);
	}

	public Dispatch buildDispatch() {
		verify(ENDPOINT, EMAIL, PASSWORD);
		return new HttpDispatch(this);
	}

	/**
	 * Merge <tt>properties</tt> into the current properties.
	 */
	public Configuration addProperties(Properties properties) {
		this.properties.putAll(properties);
		return this;
	}

	private InputStream configurationStream(String path) {
		try {
			return this.getClass().getClassLoader().getResourceAsStream(path);
		} catch (Exception e) {
			throw new RuntimeException("Could not find file: " + path, e);
		}
	}

	/**
	 * Use the properties specified in the resource <tt>fb4api.properties</tt>.
	 */
	public Configuration configure() {
		return configure("fbapi4j.properties");
	}

	/**
	 * Use <tt>file</tt> to build the properties.
	 */
	public Configuration configure(File file) {
		try {
			return configure(new FileInputStream(file));
		} catch (IOException e) {
			throw new RuntimeException("Could not open file: " + file, e);
		}
	}

	private Configuration configure(InputStream stream) {
		try {
			this.properties.load(stream);
		} catch (IOException e) {
			throw new RuntimeException("Could not load properties from InputStream: " + e);
		}
		return this;
	}

	/**
	 * Use the properties specified in <tt>resource</tt>.
	 */
	public Configuration configure(String resource) {
		InputStream stream = configurationStream(resource);
		return configure(stream);
	}

	/**
	 * Use the properties specified in <tt>url</tt>.
	 */
	public Configuration configure(URL url) {
		try {
			return configure(url.openStream());
		} catch (IOException e) {
			throw new RuntimeException("Could not configure from URL: " + url, e);
		}
	}

	public String getEmail() {
		verify(EMAIL);

		return getProperty(EMAIL);
	}

	public URL getEndpoint() {
		verify(ENDPOINT);

		URL url = null;
		try {
			url = new URL(getProperty(ENDPOINT));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return url;
	}

	public String getPassword() {
		verify(PASSWORD);

		return getProperty(PASSWORD);
	}

	public Properties getProperties() {
		Properties properties = new Properties();
		properties.putAll(this.properties);
		return properties;
	}

	public String getProperty(String name) {
		return properties.getProperty(name);
	}

	public String getPath() {
		return properties.getProperty(PATH);
	}

	public URL getUrl() {
		verify(PATH);

		URL url = null;
		try {
			url = new URL(getEndpoint(), getProperty(PATH));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return url;
	}

	/**
	 * Override current properties with new <tt>properties</tt>.
	 */
	public Configuration setProperties(Properties properties) {
		this.properties = properties;
		return this;
	}

	/**
	 * Set a property.
	 */
	public Configuration setProperty(String name, String value) {
		properties.setProperty(name, value);
		return this;
	}

	private void verify(String... properties) {
		for (String prop : properties) {
			if (!this.properties.containsKey(prop)) {
				throw new IllegalStateException("Properties file missing property '" + prop + "'");
			}
		}
	}
}
