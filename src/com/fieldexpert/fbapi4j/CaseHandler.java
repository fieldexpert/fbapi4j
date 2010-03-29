package com.fieldexpert.fbapi4j;

import static com.fieldexpert.fbapi4j.common.StringUtil.collectionToCommaDelimitedString;
import static java.util.Arrays.asList;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fieldexpert.fbapi4j.common.Assert;
import com.fieldexpert.fbapi4j.common.Attachment;
import com.fieldexpert.fbapi4j.common.DateFormatUtil;
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

	private void allowed(Case c, AllowedOperation operation) {
		if (!c.getAllowedOperations().contains(operation)) {
			throw new Fbapi4jException("Operation " + operation.toString() + " is not allowed. [Allowed operations: " + c.getAllowedOperations() + "].");
		}
	}

	@Override
	Case build(Map<String, String> data, Document doc) {
		Case c = new Case(Integer.parseInt(data.get(Fbapi4j.IX_BUG)), data.get(Fbapi4j.S_PROJECT), data.get(Fbapi4j.S_AREA), //
				data.get(Fbapi4j.S_TITLE), data.get(Fbapi4j.S_SCOUT_DESCRIPTION));
		List<Event> events = events(doc, c);
		c.addEvents(events);
		update(c, data);
		return c;
	}

	public void close(Case bug) {
		allowed(bug, AllowedOperation.CLOSE);
		sendAndUpdate(Fbapi4j.CLOSE, bug, util.map(Fbapi4j.IX_BUG, bug.getId()));
	}

	public void create(Case bug) {
		sendAndUpdate(Fbapi4j.NEW, bug);
	}

	public void edit(Case bug) {
		Assert.notNull(bug.getId());
		allowed(bug, AllowedOperation.EDIT);
		sendAndUpdate(Fbapi4j.EDIT, bug);
	}

	private Map<String, Object> events(Case c) {
		return new HashMap<String, Object>(c.getFields());
	}

	private List<Event> events(Document doc, Case c) {
		List<Event> events = new ArrayList<Event>();
		NodeList nodes = doc.getElementsByTagName("event");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			Map<String, String> map = util.children(element);
			try {
				Event event = new Event(map.get(Fbapi4j.IX_BUG_EVENT), c, map.get(Fbapi4j.S_VERB), //
						map.get(Fbapi4j.EVN_DESC), DateFormatUtil.parse(map.get(Fbapi4j.DT)), map.get(Fbapi4j.S_PERSON));

				NodeList attachList = element.getElementsByTagName("attachment");
				for (int j = 0; j < attachList.getLength(); j++) {
					Map<String, String> attachMap = util.children(attachList.item(j));
					URL u = new URL(dispatch.getEndpoint(), attachMap.get(Fbapi4j.S_URL).replaceAll("&amp;", "&") + "&token=" + token);
					event.attach(new EventAttachment(attachMap.get(Fbapi4j.S_FILENAME), u));
				}
				events.add(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return events;
	}

	public List<Case> findAll() {
		return query();
	}

	public Case findById(Integer id) {
		return find(new Request(Fbapi4j.SEARCH, util.map(Fbapi4j.TOKEN, token, Fbapi4j.QUERY, id, Fbapi4j.COLS, cols)));
	}

	public Case findByName(String name) {
		throw new UnsupportedOperationException("Not *yet* supported");
	}

	public List<Case> query(String... criterion) {
		String q = collectionToCommaDelimitedString(asList(criterion));
		Response resp = dispatch.invoke(new Request(Fbapi4j.SEARCH, util.map(Fbapi4j.TOKEN, token, Fbapi4j.COLS, cols, Fbapi4j.QUERY, q)));
		return list(resp);
	}

	public void reactivate(Case bug) {
		Assert.notNull(bug.getId());
		allowed(bug, AllowedOperation.REACTIVATE);
		sendAndUpdate(Fbapi4j.REACTIVATE, bug);
	}

	public void reopen(Case bug) {
		Assert.notNull(bug.getId());
		allowed(bug, AllowedOperation.REOPEN);
		sendAndUpdate(Fbapi4j.REOPEN, bug);
	}

	public void resolve(Case bug) {
		Assert.notNull(bug.getId());
		allowed(bug, AllowedOperation.RESOLVE);
		sendAndUpdate(Fbapi4j.RESOLVE, bug);
	}

	void scout(Case bug) {
		sendAndUpdate(Fbapi4j.NEW, bug, util.map(Fbapi4j.S_SCOUT_DESCRIPTION, bug.getTitle()));
	}

	private Response send(String command, Map<String, Object> parameters, List<Attachment> attachments) {
		parameters.put(Fbapi4j.TOKEN, token);
		Request request = new Request(command, parameters);
		if (attachments != null) {
			request.attach(attachments);
		}
		return dispatch.invoke(request);
	}

	private Response sendAndUpdate(String command, Case bug) {
		Response resp = send(command, events(bug), bug.getAttachments());
		update(bug, util.data(resp.getDocument(), "case").get(0));
		return resp;
	}

	private Response sendAndUpdate(String command, Case bug, Map<String, Object> params) {
		params.putAll(events(bug));
		Response resp = send(command, params, bug.getAttachments());
		update(bug, util.data(resp.getDocument(), "case").get(0));
		return resp;
	}

	private void update(Case c, Map<String, String> data) {
		c.setId(Integer.parseInt(data.get(Fbapi4j.IX_BUG)));
		List<String> allowed = StringUtil.commaDelimitedStringToList(data.get(Fbapi4j.OPERATIONS));
		Set<AllowedOperation> operations = new HashSet<AllowedOperation>();
		for (String op : allowed) {
			operations.add(AllowedOperation.valueOf(op.toUpperCase()));
		}
		c.setAllowedOperations(operations);
	}
}
