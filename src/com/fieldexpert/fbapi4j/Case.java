package com.fieldexpert.fbapi4j;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fieldexpert.fbapi4j.common.Assert;
import com.fieldexpert.fbapi4j.common.Attachment;

public class Case {

	private final String project;
	private final String area;
	private final String title;
	private final String description;
	private final List<Attachment> attachments;

	public Case(String project, String area, String title, String description) {
		this.project = project;
		this.area = area;
		this.title = title;
		this.description = description;
		this.attachments = new ArrayList<Attachment>();
	}

	public Case attach(Attachment attachment) {
		Assert.notNull(attachment);
		this.attachments.add(attachment);
		return this;
	}

	public Case attach(File file) {
		return attach(new Attachment(file));
	}

	public Case attach(List<Attachment> attachments) {
		Assert.notNull(attachments);
		for (Attachment attachment : attachments) {
			attach(attachment);
		}
		return this;
	}

	public Case attach(String filename, String type, byte[] content) {
		return attach(new Attachment(filename, type, content));
	}

	public Case attach(String filename, String type, InputStream content) {
		return attach(new Attachment(filename, type, content));
	}

	public Case attach(String filename, String type, String content) {
		return attach(new Attachment(filename, type, content.getBytes(Charset.forName("UTF-8"))));
	}

	public String getProject() {
		return project;
	}

	public String getArea() {
		return area;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	List<Attachment> getAttachments() {
		return Collections.unmodifiableList(this.attachments);
	}

	@Override
	public String toString() {
		return "Case [area=" + area + ", description=" + description + ", project=" + project + ", title=" + title + "]";
	}
}
