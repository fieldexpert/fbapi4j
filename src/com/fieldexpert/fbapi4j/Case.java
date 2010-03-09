package com.fieldexpert.fbapi4j;

import static com.fieldexpert.fbapi4j.common.StringUtil.collectionToCommaDelimitedString;
import static java.util.Arrays.asList;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fieldexpert.fbapi4j.common.Assert;
import com.fieldexpert.fbapi4j.common.Attachment;
import com.fieldexpert.fbapi4j.common.DateFormatUtil;

public class Case {
	private List<Attachment> attachments;
	private List<Event> events;
	private Set<AllowedOperation> allowedOperations;

	private Map<String, Object> _events = new HashMap<String, Object>();

	public Case(String project, String area, String title, String description) {
		this(null, project, area, title, description);
	}

	Case(String number, String project, String area, String title, String description) {
		this(number, project, area, title, description, new ArrayList<Event>());
	}

	Case(String number, String project, String area, String title, String description, List<Event> events) {
		_events.put(Fbapi4j.S_PROJECT, project);
		_events.put(Fbapi4j.S_AREA, area);
		_events.put(Fbapi4j.S_TITLE, title);
		_events.put(Fbapi4j.S_EVENT, description);
		_events.put(Fbapi4j.IX_BUG, number);
		this.attachments = new ArrayList<Attachment>();
		this.events = events;
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

	@SuppressWarnings("unused")
	// Used by reflection
	private void setNumber(String number) {
		_events.put(Fbapi4j.IX_BUG, number);
	}

	public String getNumber() {
		return (String) _events.get(Fbapi4j.IX_BUG);
	}

	public String getProject() {
		return (String) _events.get(Fbapi4j.S_PROJECT);
	}

	public String getArea() {
		return (String) _events.get(Fbapi4j.S_AREA);
	}

	public String getTitle() {
		return (String) _events.get(Fbapi4j.S_TITLE);
	}

	public String getScoutDescription() {
		return (String) _events.get(Fbapi4j.S_SCOUT_DESCRIPTION);
	}

	public List<Attachment> getAttachments() {
		return Collections.unmodifiableList(this.attachments);
	}

	public List<Event> getEvents() {
		return Collections.unmodifiableList(this.events);
	}

	public void setArea(String area) {
		_events.put(Fbapi4j.S_AREA, area);
	}

	public void setParent(Case parent) {
		if (parent.getNumber() == null) {
			throw new Fbapi4jException("The parent case must be persisted first");
		}
		_events.put(Fbapi4j.IX_BUG_PARENT, parent.getNumber());
	}

	public void setTags(String... tags) {
		_events.put(Fbapi4j.S_TAGS, collectionToCommaDelimitedString(asList(tags)));
	}

	public void setDescription(String description) {
		_events.put(Fbapi4j.S_EVENT, description);
	}

	public void setPriority(Priority priority) {
		_events.put(Fbapi4j.S_PRIORITY, priority.getValue());
	}

	public void setDueDate(Date date) {
		_events.put(Fbapi4j.DT_DUE, DateFormatUtil.format(date));
	}

	public void setProject(String project) {
		_events.put(Fbapi4j.S_PROJECT, project);
	}

	public void setHoursEstimate(int hours) {
		_events.put(Fbapi4j.HRS_CURR_EST, hours);
	}

	public void setTitle(String title) {
		_events.put(Fbapi4j.S_TITLE, title);
	}

	void addEvents(List<Event> events) {
		this.events = events;
	}

	public Set<AllowedOperation> getAllowedOperations() {
		return allowedOperations;
	}

	public void setAssignedTo(String person) {
		_events.put(Fbapi4j.S_PERSON_ASSIGNED_TO, person);
	}

}
