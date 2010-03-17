package com.fieldexpert.fbapi4j;

import static com.fieldexpert.fbapi4j.common.StringUtil.collectionToCommaDelimitedString;
import static java.util.Arrays.asList;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fieldexpert.fbapi4j.common.Assert;
import com.fieldexpert.fbapi4j.common.Attachment;
import com.fieldexpert.fbapi4j.common.DateFormatUtil;

@EntityConfig(element = "case", list = Fbapi4j.QUERY, single = Fbapi4j.QUERY, id = Fbapi4j.IX_BUG)
public class Case extends Entity {
	private List<Attachment> attachments;
	private List<Event> events;
	private Set<AllowedOperation> allowedOperations;

	public Case(String project, String area, String title, String description) {
		this(null, project, area, title, description);
	}

	Case(Integer id, String project, String area, String title, String description) {
		this(id, project, area, title, description, new ArrayList<Event>());
	}

	Case(Integer id, String project, String area, String title, String description, List<Event> events) {
		fields.put(Fbapi4j.S_PROJECT, project);
		fields.put(Fbapi4j.S_AREA, area);
		fields.put(Fbapi4j.S_TITLE, title);
		fields.put(Fbapi4j.S_EVENT, description);
		fields.put(Fbapi4j.IX_BUG, id);
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

	void setAllowedOperations(Set<AllowedOperation> allowedOperations) {
		this.allowedOperations = allowedOperations;
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

	void setId(Integer id) {
		fields.put(Fbapi4j.IX_BUG, id);
	}

	public Integer getId() {
		return (Integer) fields.get(Fbapi4j.IX_BUG);
	}

	public String getProject() {
		return (String) fields.get(Fbapi4j.S_PROJECT);
	}

	public String getArea() {
		return (String) fields.get(Fbapi4j.S_AREA);
	}

	public String getTitle() {
		return (String) fields.get(Fbapi4j.S_TITLE);
	}

	public String getScoutDescription() {
		return (String) fields.get(Fbapi4j.S_SCOUT_DESCRIPTION);
	}

	public List<Attachment> getAttachments() {
		return Collections.unmodifiableList(this.attachments);
	}

	public List<Event> getEvents() {
		return Collections.unmodifiableList(this.events);
	}

	public void setArea(String area) {
		fields.put(Fbapi4j.S_AREA, area);
	}

	public void setParent(Case parent) {
		if (parent.getId() == null) {
			throw new Fbapi4jException("The parent case must be persisted first");
		}
		fields.put(Fbapi4j.IX_BUG_PARENT, parent.getId());
	}

	public void setTags(String... tags) {
		fields.put(Fbapi4j.S_TAGS, collectionToCommaDelimitedString(asList(tags)));
	}

	public void setDescription(String description) {
		fields.put(Fbapi4j.S_EVENT, description);
	}

	public void setPriority(int priority) {
		if (priority < 1 || priority > 7) {
			throw new IllegalArgumentException("Priority must be between 1 and 7");
		}
		fields.put(Fbapi4j.IX_PRIORITY, priority);
	}

	public void setPriority(Priority priority) {
		setPriority(priority.getId().intValue());
	}

	public void setDueDate(Date date) {
		fields.put(Fbapi4j.DT_DUE, DateFormatUtil.format(date));
	}

	public void setProject(String project) {
		fields.put(Fbapi4j.S_PROJECT, project);
	}

	public void setHoursEstimate(int hours) {
		fields.put(Fbapi4j.HRS_CURR_EST, hours);
	}

	Map<String, Object> getFields() {
		return fields;
	}

	public void setTitle(String title) {
		fields.put(Fbapi4j.S_TITLE, title);
	}

	void addEvents(List<Event> events) {
		this.events = events;
	}

	public Set<AllowedOperation> getAllowedOperations() {
		return allowedOperations;
	}

	public void setAssignedTo(String person) {
		fields.put(Fbapi4j.S_PERSON_ASSIGNED_TO, person);
	}

}
