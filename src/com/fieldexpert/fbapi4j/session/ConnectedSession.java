package com.fieldexpert.fbapi4j.session;

import java.util.List;

import org.w3c.dom.Document;

import com.fieldexpert.fbapi4j.Case;
import com.fieldexpert.fbapi4j.FogBugz;
import com.fieldexpert.fbapi4j.common.Assert;
import com.fieldexpert.fbapi4j.common.Attachment;
import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;
import com.fieldexpert.fbapi4j.dispatch.Request;
import com.fieldexpert.fbapi4j.dispatch.Response;
import com.fieldexpert.fbapi4j.http.Http;

class ConnectedSession implements Session {

	private Dispatch dispatch;
	private Util util;

	private String token;
	private String url;

	ConnectedSession(Dispatch dispatch) {
		this.dispatch = dispatch;
		this.util = new Util();
	}

	private void api() {
		if (url == null) {
			Response response = dispatch.invoke(Http.GET, util.url(dispatch.getEndpoint(), FogBugz.API_XML));
			Document doc = response.getDocument();
			url = util.children(doc).get("url");
			dispatch.setProperty("path", url);
		}
	}

	public void assign(Case bug) {
		// TODO Auto-generated method stub
	}

	public void close() {
		Assert.notNull(token);
		dispatch.invoke(new Request(FogBugz.LOGOFF, util.map(FogBugz.TOKEN, token)));
		token = null;
	}

	public void close(Case bug) {
		// TODO Auto-generated method stub

	}

	public void create(Case bug) {
		Assert.notNull(token);

		String project = bug.getProject();
		String area = bug.getArea();
		String title = bug.getTitle();
		String desc = bug.getDescription();
		List<Attachment> attachments = bug.getAttachments();

		if (attachments == null) {
			dispatch.invoke(new Request(FogBugz.NEW, util.map(FogBugz.TOKEN, token, //
					FogBugz.S_PROJECT, project, FogBugz.S_AREA, area, FogBugz.S_SCOUT_DESCRIPTION, title, //
					FogBugz.S_TITLE, title, FogBugz.S_EVENT, desc)));
		} else {
			dispatch.invoke(new Request(FogBugz.NEW, util.map(FogBugz.TOKEN, token, //
					FogBugz.S_PROJECT, project, FogBugz.S_AREA, area, FogBugz.S_SCOUT_DESCRIPTION, title, //
					FogBugz.S_TITLE, title, FogBugz.S_EVENT, desc)).attach(attachments));
		}
	}

	public void edit(Case bug) {
		// TODO Auto-generated method stub

	}

	void logon() {
		api();
		Response resp = dispatch.invoke(new Request(FogBugz.LOGON, util.map(FogBugz.EMAIL, dispatch.getEmail(), FogBugz.PASSWORD, dispatch.getPassword())));
		Document doc = resp.getDocument();
		token = util.children(doc).get("token");
	}

	public void reactivate(Case bug) {
		// TODO Auto-generated method stub

	}

	public void reopen(Case bug) {
		// TODO Auto-generated method stub

	}

	public void resolve(Case bug) {
		// TODO Auto-generated method stub

	}

}
