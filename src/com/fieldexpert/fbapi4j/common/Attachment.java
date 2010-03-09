package com.fieldexpert.fbapi4j.common;

import static eu.medsea.mimeutil.MimeUtil.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collection;

import eu.medsea.mimeutil.detector.ExtensionMimeDetector;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;

public class Attachment {

	static {
		registerMimeDetector(ExtensionMimeDetector.class.getName());
		registerMimeDetector(MagicMimeMimeDetector.class.getName());
	}

	public static String getType(File file) {
		Collection<?> types = getMimeTypes(file);
		return getMostSpecificMimeType(types).toString();
	}

	public static String getType(InputStream is) {
		Collection<?> types = getMimeTypes(is);
		return getMostSpecificMimeType(types).toString();
	}

	public static String getType(byte[] bytes) {
		Collection<?> types = getMimeTypes(bytes);
		return getMostSpecificMimeType(types).toString();
	}

	private String filename;
	private String type;
	private InputStream content;

	// not for you
	protected Attachment(String name) {
		this.filename = name;
	}

	public Attachment(String filename, String type, byte[] content) {
		this(filename, type, new ByteArrayInputStream(content));
	}

	public Attachment(String filename, String type, String content) {
		this(filename, type, content.getBytes(Charset.forName("UTF-8")));
	}

	public Attachment(String filename, String type, InputStream content) {
		this.filename = filename;
		this.type = type;
		this.content = content;
	}

	// can't chain this constructor call, because we need to try/catch
	public Attachment(File file) {
		Assert.notNull(file);

		this.filename = file.getName();
		this.type = getType(file);
		try {
			this.content = new FileInputStream(file);
		} catch (Exception e) {
			throw new RuntimeException("Error reading file: '" + file.getName() + "'");
		}
	}

	public InputStream getContent() {
		return content;
	}

	public String getFilename() {
		return filename;
	}

	public String getType() {
		return type;
	}
}
