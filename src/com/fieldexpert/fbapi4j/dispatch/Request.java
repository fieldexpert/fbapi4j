package com.fieldexpert.fbapi4j.dispatch;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fieldexpert.fbapi4j.Fbapi4j;
import com.fieldexpert.fbapi4j.common.Assert;
import com.fieldexpert.fbapi4j.common.Attachment;

public final class Request implements Map<String, Object> {

	private Map<String, Object> parameters;
	private List<Attachment> attachments;

	public Request() {
		this(null, null, null);
	}

	public Request(Map<String, Object> parameters) {
		this(null, parameters, null);
	}

	public Request(String command) {
		this(command, null, null);
	}

	public Request(String command, Map<String, Object> parameters) {
		this(command, parameters, null);
	}

	public Request(String command, Map<String, Object> parameters, List<Attachment> attachments) {
		if (attachments != null && !attachments.isEmpty()) {
			this.attachments = new ArrayList<Attachment>(attachments);
		} else {
			this.attachments = new ArrayList<Attachment>();
		}

		if (parameters != null && !parameters.isEmpty()) {
			this.parameters = new HashMap<String, Object>(parameters);
		} else {
			this.parameters = new HashMap<String, Object>();
		}

		if (command != null && !command.isEmpty()) {
			this.setCommand(command);
		}
	}

	public Request attach(Attachment attachment) {
		Assert.notNull(attachment);
		this.attachments.add(attachment);
		return this;
	}

	public Request attach(File file) {
		return attach(new Attachment(file));
	}

	public Request attach(List<Attachment> attachments) {
		Assert.notNull(attachments);
		for (Attachment attachment : attachments) {
			attach(attachment);
		}
		return this;
	}

	public Request attach(String filename, String type, byte[] content) {
		return attach(new Attachment(filename, type, content));
	}

	public Request attach(String filename, String type, InputStream content) {
		return attach(new Attachment(filename, type, content));
	}

	public Request attach(String filename, String type, String content) {
		return attach(new Attachment(filename, type, content.getBytes(Charset.forName("UTF-8"))));
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public Request setCommand(String command) {
		parameters.put(Fbapi4j.CMD, command);
		return this;
	}

	// ---- delegate calls to parameters ---- //

	public void clear() {
		parameters.clear();
	}

	public boolean containsKey(Object key) {
		return parameters.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return parameters.containsValue(value);
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return parameters.entrySet();
	}

	public Object get(Object key) {
		return parameters.get(key);
	}

	public boolean isEmpty() {
		return parameters.isEmpty();
	}

	public Set<String> keySet() {
		return parameters.keySet();
	}

	public Object put(String key, Object value) {
		return parameters.put(key, value);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		parameters.putAll(m);
	}

	public Object remove(Object key) {
		return parameters.remove(key);
	}

	public int size() {
		return parameters.size();
	}

	public Collection<Object> values() {
		return parameters.values();
	}
}
