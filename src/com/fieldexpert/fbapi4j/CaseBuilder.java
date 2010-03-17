package com.fieldexpert.fbapi4j;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fieldexpert.fbapi4j.common.DateFormatUtil;
import com.fieldexpert.fbapi4j.common.StringUtil;
import com.fieldexpert.fbapi4j.common.Util;

class CaseBuilder {
	private Util util;
	private URL url;
	private String token;

	CaseBuilder(Util util, URL url, String token) {
		this.util = util;
		this.url = url;
		this.token = token;
	}

	// TODO cleanup this entire file

	List<Case> list(Document doc) {
		List<Case> cases = new ArrayList<Case>();
		for (Map<String, String> caseMap : util.data(doc, "case")) {
			Case c = new Case(Integer.parseInt(caseMap.get(Fbapi4j.IX_BUG)), caseMap.get(Fbapi4j.S_PROJECT), caseMap.get(Fbapi4j.S_AREA), //
					caseMap.get(Fbapi4j.S_TITLE), caseMap.get(Fbapi4j.S_SCOUT_DESCRIPTION));
			List<Event> events = events(doc, c);
			c.addEvents(events);

			cases.add(c);
		}
		return cases;
	}

	private Case buildCase(Document doc, Map<String, String> caseMap) {
		Case c = new Case(Integer.parseInt(caseMap.get(Fbapi4j.IX_BUG)), caseMap.get(Fbapi4j.S_PROJECT), caseMap.get(Fbapi4j.S_AREA), //
				caseMap.get(Fbapi4j.S_TITLE), caseMap.get(Fbapi4j.S_SCOUT_DESCRIPTION));

		List<Event> events = events(doc, c);
		c.addEvents(events);

		// TODO This is repeated in CaseHandler, which is gross.
		List<String> allowed = StringUtil.commaDelimitedStringToList(caseMap.get(Fbapi4j.OPERATIONS));
		Set<AllowedOperation> operations = new HashSet<AllowedOperation>();
		for (String op : allowed) {
			operations.add(AllowedOperation.valueOf(op.toUpperCase()));
		}
		c.setAllowedOperations(operations);
		return c;
	}

	Case singleResult(Document doc) {
		Map<String, String> caseMap = util.data(doc, "case").get(0);
		return buildCase(doc, caseMap);
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
					URL u = new URL(url, attachMap.get(Fbapi4j.S_URL).replaceAll("&amp;", "&") + "&token=" + token);
					event.attach(new EventAttachment(attachMap.get(Fbapi4j.S_FILENAME), u));
				}
				events.add(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return events;
	}
}
