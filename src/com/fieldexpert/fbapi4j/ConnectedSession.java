package com.fieldexpert.fbapi4j;

import static com.fieldexpert.fbapi4j.common.StringUtil.collectionToCommaDelimitedString;
import static java.util.Arrays.asList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;

import com.fieldexpert.fbapi4j.common.Assert;
import com.fieldexpert.fbapi4j.common.Attachment;
import com.fieldexpert.fbapi4j.common.StringUtil;
import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;
import com.fieldexpert.fbapi4j.dispatch.Request;
import com.fieldexpert.fbapi4j.dispatch.Response;
import com.fieldexpert.fbapi4j.http.Http;

class ConnectedSession implements Session {
	private String token;
	private String url;
	private Dispatch dispatch;
	private Util util;

	ConnectedSession(Dispatch dispatch) {
		this.dispatch = dispatch;
		this.util = new Util();
	}

	private void api() {
		if (url == null) {
			Response response = dispatch.invoke(Http.GET, util.url(dispatch.getEndpoint(), Fbapi4j.API_XML));
			Document doc = response.getDocument();
			url = util.children(doc).get("url");
			dispatch.setProperty("path", url);
		}
	}

	public void assign(Case bug) {
		Assert.notNull(bug.getNumber());
		// TODO Make sure this case is assignable
		Response resp = send(Fbapi4j.ASSIGN, events(bug));
		updateCase(bug, util.data(resp.getDocument(), "case").get(0));
	}

	public void close() {
		Assert.notNull(token);
		dispatch.invoke(new Request(Fbapi4j.LOGOFF, util.map(Fbapi4j.TOKEN, token)));
		token = null;
	}

	public void close(Case bug) {
		Assert.notNull(token);
		if (!bug.getAllowedOperations().contains(AllowedOperation.CLOSE)) {
			throw new Fbapi4jException("This bug cannot be closed.");
		}
		Response resp = send(Fbapi4j.CLOSE, util.map(Fbapi4j.IX_BUG, bug.getNumber()));
		updateCase(bug, util.data(resp.getDocument(), "case").get(0));
	}

	private Map<String, Object> events(Case c) {
		return new HashMap<String, Object>(c.getFields());
	}

	public void create(Case bug) {
		Assert.notNull(token);
		List<Attachment> attachments = bug.getAttachments();
		Map<String, Object> parameters = events(bug);
		Response resp;

		if (attachments == null) {
			resp = send(Fbapi4j.NEW, parameters);
		} else {
			resp = send(Fbapi4j.NEW, parameters, attachments);
		}

		updateCase(bug, util.data(resp.getDocument(), "case").get(0));
	}

	private void updateCase(Case c, Map<String, String> data) {
		c.setNumber(data.get(Fbapi4j.IX_BUG));
		List<String> allowed = StringUtil.commaDelimitedStringToSet(data.get(Fbapi4j.OPERATIONS));
		Set<AllowedOperation> operations = new HashSet<AllowedOperation>();
		for (String op : allowed) {
			operations.add(AllowedOperation.valueOf(op.toUpperCase()));
		}
		c.setAllowedOperations(operations);
	}

	public void edit(Case bug) {
		Assert.notNull(bug.getNumber());
		// TODO Make sure this case is editable
		Response resp = send(Fbapi4j.EDIT, events(bug));
		updateCase(bug, util.data(resp.getDocument(), "case").get(0));
	}

	private Response send(String command, Map<String, Object> parameters) {
		return send(command, parameters, null);
	}

	private Response send(String command, Map<String, Object> parameters, List<Attachment> attachments) {
		parameters.put(Fbapi4j.TOKEN, token);

		Request request = new Request(command, parameters);
		if (attachments != null) {
			request.attach(attachments);
		}
		return dispatch.invoke(request);
	}

	void logon() {
		api();
		Response resp = dispatch.invoke(new Request(Fbapi4j.LOGON, util.map(Fbapi4j.EMAIL, dispatch.getEmail(), Fbapi4j.PASSWORD, dispatch.getPassword())));
		Document doc = resp.getDocument();
		token = util.children(doc).get("token");
	}

	public void reactivate(Case bug) {
		Assert.notNull(bug.getNumber());
		Response resp = send(Fbapi4j.REACTIVATE, events(bug));
		updateCase(bug, util.data(resp.getDocument(), "case").get(0));
	}

	public void reopen(Case bug) {
		Assert.notNull(bug.getNumber());
		Response resp = send(Fbapi4j.REOPEN, events(bug));
		updateCase(bug, util.data(resp.getDocument(), "case").get(0));
	}

	public void resolve(Case bug) {
		Assert.notNull(bug.getNumber());
		Response resp = send(Fbapi4j.RESOLVE, events(bug));
		updateCase(bug, util.data(resp.getDocument(), "case").get(0));
	}

	public void scout(Case bug) {
		Assert.notNull(token);
		List<Attachment> attachments = bug.getAttachments();
		Map<String, Object> parameters = events(bug);
		parameters.put(Fbapi4j.S_SCOUT_DESCRIPTION, bug.getTitle());

		if (attachments == null) {
			send(Fbapi4j.NEW, parameters);
		} else {
			send(Fbapi4j.NEW, parameters, attachments);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> T get(Class<T> clazz, Serializable id) {
		if (clazz.equals(Case.class)) {
			String cols = collectionToCommaDelimitedString(asList(Fbapi4j.S_PROJECT, Fbapi4j.S_AREA, Fbapi4j.S_SCOUT_DESCRIPTION, Fbapi4j.S_TITLE, Fbapi4j.S_EVENT, Fbapi4j.EVENTS));
			Response resp = dispatch.invoke(new Request(Fbapi4j.SEARCH, util.map(Fbapi4j.TOKEN, token, Fbapi4j.QUERY, id, Fbapi4j.COLS, cols)));
			return (T) new CaseBuilder(util, dispatch.getEndpoint(), token).build(resp.getDocument());
		} else if (clazz.equals(Project.class)) {
			Response resp = dispatch.invoke(new Request(Fbapi4j.LIST_PROJECTS, util.map(Fbapi4j.TOKEN, token, Fbapi4j.IX_PROJECT, id)));
			Map<String, String> project = util.data(resp.getDocument(), "project").get(0);
			return (T) new Project(Long.parseLong(project.get(Fbapi4j.IX_PROJECT)), project.get(Fbapi4j.S_PROJECT));
		} else {
			throw new IllegalArgumentException();
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Entity> List<T> findAll(Class<T> clazz) {
		if (clazz.equals(Project.class)) {
			Response resp = dispatch.invoke(new Request(Fbapi4j.LIST_PROJECTS, util.map(Fbapi4j.TOKEN, token)));
			List<Map<String, String>> list = util.data(resp.getDocument(), "project");
			List<Project> projects = new ArrayList<Project>();
			for (Map<String, String> map : list) {
				projects.add(new Project(Long.parseLong(map.get(Fbapi4j.IX_PROJECT)), map.get(Fbapi4j.S_PROJECT)));
			}
			return (List<T>) projects;
		} else {
			throw new IllegalArgumentException();
		}
	}
}
