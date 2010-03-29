package com.fieldexpert.fbapi4j;

import static junit.framework.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class ConfigurationTest {

	private Configuration configuration;
	private Properties props;

	@Test
	public void basicConfigure() throws Exception {
		configuration.configure();
		testValues();
	}

	@Test
	public void resourceConfigure() throws Exception {
		configuration.configure("fbapi4j.properties");
		testValues();
	}

	@Test
	public void fileConfigure() throws Exception {
		configuration.configure(new File("test/fbapi4j.properties"));
		testValues();
	}

	@Test
	public void urlConfigure() throws Exception {
		URI uri = new File("test/fbapi4j.properties").toURI();
		configuration.configure(uri.toURL());
		testValues();
	}

	@Test
	public void getProperties() throws Exception {
		configuration.configure();
		assertEquals(props, configuration.getProperties());
	}

	private void testValues() throws Exception {
		assertEquals(props.getProperty("email"), configuration.getProperty("email"));
		assertEquals(props.getProperty("password"), configuration.getProperty("password"));
		assertEquals(props.getProperty("path"), configuration.getProperty("path"));
		assertEquals(props.getProperty("endpoint"), configuration.getProperty("endpoint"));

		assertEquals(configuration.getProperty("email"), configuration.getEmail());
		assertEquals(configuration.getProperty("password"), configuration.getPassword());
		assertEquals(configuration.getProperty("path"), configuration.getPath());

		URL endpoint = new URL(props.getProperty("endpoint"));
		assertEquals(endpoint, configuration.getEndpoint());
		assertEquals(new URL(endpoint, props.getProperty("path")), configuration.getUrl());
	}

	@Before
	public void setup() throws Exception {
		this.configuration = new Configuration();
		configuration.setProperty("path", "api.asp?");
		this.props = new Properties();
		props.load(new FileReader("test/fbapi4j.properties"));
		props.setProperty("path", "api.asp?");
	}
}
