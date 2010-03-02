package com.fieldexpert.fbapi4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import com.fieldexpert.fbapi4j.common.Assert;
import com.fieldexpert.fbapi4j.common.Attachment;
import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;

public class Submitter {

	public static void submit(Configuration configuration, Case bug) {
		Submitter submit = new Submitter(configuration);
		submit.logon();
		submit.submit(bug);
		submit.logoff();
	}

	private Dispatch dispatch;
	private String token;
	private String url;
	private Util util;

	private Operations operations;

	public Submitter(Configuration configuration) {
		this.dispatch = configuration.buildDispatch();
		this.operations = new Operations(dispatch);

		this.token = null;
		this.util = new Util();
	}

	private void api() {
		if (url == null) {
			Document response = operations.api();
			url = util.children(response).get("url");
			dispatch.setProperty("path", url);
		}
	}

	public void logon() {
		api();

		if (token == null) {
			Document response = operations.logon();
			token = util.children(response).get("token");
		}
	}

	public void submit(Case bug) {
		Assert.notNull(token, "Not logged in!");

		String project = bug.getProject();
		String area = bug.getArea();
		String title = bug.getTitle();
		String desc = bug.getDescription();
		List<Attachment> attachments = bug.getAttachments();

		if (attachments == null) {
			operations.scout(token, project, area, title, desc);
		} else {
			operations.scout(token, project, area, title, desc, attachments);
		}
	}

	public List<Case> cases(String query) {
		Assert.notNull(token, "Not logged in!");

		List<Case> cases = new ArrayList<Case>();
		Document document = operations.search(token, query);
		for (Map<String, String> map : util.data(document, "case")) {
			String project = map.get(FogBugz.S_PROJECT);
			String area = map.get(FogBugz.S_AREA);
			String title = map.get(FogBugz.S_TITLE);
			String description = map.get(FogBugz.S_EVENT);
			cases.add(new Case(project, area, title, description));
		}
		return cases;
	}

	public void logoff() {
		if (token != null) {
			operations.logoff(token);
			token = null;
		}
	}
}
