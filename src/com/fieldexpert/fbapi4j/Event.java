package com.fieldexpert.fbapi4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fieldexpert.fbapi4j.common.Attachment;

public class Event implements Comparable<Event> {
	private final String id;
	private final Case bug;
	private final String verb;
	private final String description;
	private final Date date;
	private final String createdBy;
	private List<EventAttachment> attachments;

	Event(String id, Case bug, String verb, String description, Date date, String createdBy) {
		this(id, bug, verb, description, date, createdBy, null);
	}

	Event(String id, Case bug, String verb, String description, Date date, String createdBy, List<EventAttachment> attachments) {
		this.id = id;
		this.bug = bug;
		this.verb = verb;
		this.description = description;
		this.date = date;
		this.createdBy = createdBy;
		this.attachments = attachments;
	}

	public String getId() {
		return id;
	}

	public Case getBug() {
		return bug;
	}

	public String getVerb() {
		return verb;
	}

	public String getDescription() {
		return description;
	}

	public Date getDate() {
		return date;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public List<? extends Attachment> getAttachments() {
		return Collections.unmodifiableList(this.attachments);
	}

	void attach(EventAttachment attachment) {
		if (this.attachments == null) {
			this.attachments = new ArrayList<EventAttachment>();
		}
		attachments.add(attachment);
	}

	public int compareTo(Event event) {
		return this.getDate().compareTo(event.getDate());
	}

}
