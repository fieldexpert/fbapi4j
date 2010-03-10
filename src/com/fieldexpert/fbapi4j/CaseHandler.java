package com.fieldexpert.fbapi4j;

import static com.fieldexpert.fbapi4j.common.StringUtil.collectionToCommaDelimitedString;
import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fieldexpert.fbapi4j.common.Assert;
import com.fieldexpert.fbapi4j.common.Attachment;
import com.fieldexpert.fbapi4j.common.StringUtil;
import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;
import com.fieldexpert.fbapi4j.dispatch.Request;
import com.fieldexpert.fbapi4j.dispatch.Response;

class CaseHandler extends AbstractHandler<Case> {

	private static final String cols = collectionToCommaDelimitedString(asList(Fbapi4j.S_PROJECT, Fbapi4j.S_AREA, Fbapi4j.S_SCOUT_DESCRIPTION, Fbapi4j.S_TITLE, Fbapi4j.S_EVENT, Fbapi4j.EVENTS));

	CaseHandler(Dispatch dispatch, Util util, String token) {
		super(dispatch, util, token);
	}

	private Map<String, Object> events(Case c) {
		return new HashMap<String, Object>(c.getFields());
	}

	public void create(Case t) {
		Assert.notNull(token);
		Case bug = (Case) t;
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
		c.setNumber(Integer.parseInt(data.get(Fbapi4j.IX_BUG)));
		List<String> allowed = StringUtil.commaDelimitedStringToSet(data.get(Fbapi4j.OPERATIONS));
		Set<AllowedOperation> operations = new HashSet<AllowedOperation>();
		for (String op : allowed) {
			operations.add(AllowedOperation.valueOf(op.toUpperCase()));
		}
		c.setAllowedOperations(operations);
	}

	public List<Case> query(String... criterion) {
		String q = collectionToCommaDelimitedString(asList(criterion));
		Response resp = dispatch.invoke(new Request(Fbapi4j.SEARCH, util.map(Fbapi4j.TOKEN, token, Fbapi4j.COLS, cols, Fbapi4j.QUERY, q)));
		return new CaseBuilder(util, dispatch.getEndpoint(), token).list(resp.getDocument());
	}

	public List<Case> findAll() {
		return query();
	}

	public Case findById(Integer id) {
		Response resp = dispatch.invoke(new Request(Fbapi4j.SEARCH, util.map(Fbapi4j.TOKEN, token, Fbapi4j.QUERY, id, Fbapi4j.COLS, cols)));
		return new CaseBuilder(util, dispatch.getEndpoint(), token).singleResult(resp.getDocument());
	}

	void scout(Case bug) {
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

	public void resolve(Case bug) {
		Assert.notNull(bug.getNumber());
		Response resp = send(Fbapi4j.RESOLVE, events(bug));
		updateCase(bug, util.data(resp.getDocument(), "case").get(0));
	}

	public void reopen(Case bug) {
		Assert.notNull(bug.getNumber());
		Response resp = send(Fbapi4j.REOPEN, events(bug));
		updateCase(bug, util.data(resp.getDocument(), "case").get(0));
	}

	public void reactivate(Case bug) {
		Assert.notNull(bug.getNumber());
		Response resp = send(Fbapi4j.REACTIVATE, events(bug));
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

	public void edit(Case bug) {
		Assert.notNull(bug.getNumber());
		// TODO Make sure this case is editable
		Response resp = send(Fbapi4j.EDIT, events(bug));
		updateCase(bug, util.data(resp.getDocument(), "case").get(0));
	}

	public void close(Case bug) {
		Assert.notNull(token);
		if (!bug.getAllowedOperations().contains(AllowedOperation.CLOSE)) {
			throw new Fbapi4jException("This bug cannot be closed.");
		}
		Response resp = send(Fbapi4j.CLOSE, util.map(Fbapi4j.IX_BUG, bug.getNumber()));
		updateCase(bug, util.data(resp.getDocument(), "case").get(0));
	}
}
