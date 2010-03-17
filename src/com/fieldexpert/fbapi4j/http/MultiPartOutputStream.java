package com.fieldexpert.fbapi4j.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fieldexpert.fbapi4j.common.Assert;

class MultiPartOutputStream {

	private static final String NEWLINE = "\r\n";
	private static final String PREFIX = "--";

	private final DataOutputStream out;
	private final String boundary;

	MultiPartOutputStream(OutputStream os, String boundary) {
		if (os == null) {
			throw new IllegalArgumentException("Outputstream cannot be null.");
		}
		this.out = new DataOutputStream(os);
		this.boundary = boundary;
	}

	void writeEnd() throws IOException {
		out.writeBytes(PREFIX);
		out.writeBytes(boundary);
		out.writeBytes(PREFIX);
		out.writeBytes(NEWLINE);
		out.flush();
	}

	private String contentDisposition(String name) {
		return "Content-Disposition: form-data; name=\"" + name + "\"";
	}

	void write(String name, Object value) throws IOException {
		Assert.notNull(name);
		if (value == null) {
			value = "";
		}

		writeBoundary();
		out.writeBytes(contentDisposition(name));
		out.writeBytes(NEWLINE);
		out.writeBytes(NEWLINE);
		out.writeBytes(value.toString());
		out.writeBytes(NEWLINE);
		out.flush();
	}

	void write(String name, String filename, String type, InputStream is) throws IOException {
		Assert.notNull(name);
		Assert.notNull(filename);
		Assert.notNull(type);
		Assert.notNull(is);

		writeBoundary();

		// header
		out.writeBytes(contentDisposition(name) + "; filename=\"" + filename + "\"");
		out.writeBytes(NEWLINE);
		if (type != null) {
			out.writeBytes("Content-Type: " + type);
			out.writeBytes(NEWLINE);
		}
		out.writeBytes(NEWLINE);

		// write data
		byte[] data = new byte[1024];
		int r = 0;
		while ((r = is.read(data, 0, data.length)) != -1) {
			out.write(data, 0, r);
		}
		is.close();
		out.writeBytes(NEWLINE);
		out.flush();
	}

	private void writeBoundary() throws IOException {
		out.writeBytes(PREFIX);
		out.writeBytes(boundary);
		out.writeBytes(NEWLINE);
	}

}
