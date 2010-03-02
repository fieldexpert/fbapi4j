package com.fieldexpert.fbapi4j.common;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Util {

	private DocumentBuilder builder;

	public Util() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, Object> map(Object... keyvals) {
		if (keyvals == null) {
			throw new IllegalArgumentException("keyvals cannot be null");
		}
		if (keyvals.length % 2 != 0) {
			throw new IllegalArgumentException("keyvals must contain an even number of strings");
		}

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		int pair = 0;
		while (pair * 2 < keyvals.length) {
			Object key = keyvals[2 * pair];
			Object val = keyvals[2 * pair + 1];

			map.put((String) key, val);
			pair++;
		}

		return map;
	}

	public Map<String, String> children(Node node) {
		Map<String, String> children = new LinkedHashMap<String, String>();
		NodeList nodes = node.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				NodeList kids = n.getChildNodes();
				if (kids.getLength() == 1) {
					Node kid = kids.item(0);
					if (kid.getNodeType() == Node.TEXT_NODE || kid.getNodeType() == Node.CDATA_SECTION_NODE) {
						children.put(n.getNodeName(), kid.getNodeValue());
					}
				}
			}
		}
		return children;
	}

	public Map<String, String> merge(Map<String, String> a, Map<String, String> b) {
		Map<String, String> result = new HashMap<String, String>(a.size() + b.size());
		result.putAll(a);
		result.putAll(b);
		return result;
	}

	public Map<String, String> attributes(Node node) {
		Map<String, String> attributes = new HashMap<String, String>();
		NamedNodeMap map = node.getAttributes();
		for (int j = 0; j < map.getLength(); j++) {
			Node n = map.item(j);
			attributes.put(n.getNodeName(), n.getTextContent());
		}
		return attributes;
	}

	public Map<String, String> children(Document document) {
		return children(document.getDocumentElement());
	}

	public List<Map<String, String>> data(Document document, String name) {
		List<Map<String, String>> children = new ArrayList<Map<String, String>>();
		NodeList nodes = document.getElementsByTagName(name);
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			children.add(merge(children(node), attributes(node)));
		}
		return children;
	}

	public Document document(InputStream input) {
		Document document = null;
		try {
			document = builder.parse(input);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return document;
	}

	public String string(Document doc) {
		StringWriter writer = new StringWriter();
		try {
			TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(writer));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return writer.toString();
	}

	public URL url(URL context, String spec) {
		URL url = null;
		try {
			url = new URL(context, spec);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return url;
	}
}
