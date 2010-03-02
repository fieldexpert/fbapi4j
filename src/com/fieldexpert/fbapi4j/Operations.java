package com.fieldexpert.fbapi4j;

import static com.fieldexpert.fbapi4j.common.StringUtil.collectionToCommaDelimitedString;
import static java.util.Arrays.asList;

import java.util.List;

import org.w3c.dom.Document;

import com.fieldexpert.fbapi4j.common.Attachment;
import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;
import com.fieldexpert.fbapi4j.dispatch.Request;
import com.fieldexpert.fbapi4j.dispatch.Response;
import com.fieldexpert.fbapi4j.http.Http;

class Operations {

	private Dispatch dispatch;
	private Util util = new Util();

	Operations(Dispatch dispatch) {
		this.dispatch = dispatch;
	}

	Document api() {
		Response resp = dispatch.invoke(Http.GET, util.url(dispatch.getEndpoint(), FogBugz.API_XML));
		return resp.getDocument();
	}

	Document logon() {
		Response resp = dispatch.invoke(new Request(FogBugz.LOGON, util.map(FogBugz.EMAIL, dispatch.getEmail(), FogBugz.PASSWORD, dispatch.getPassword())));
		return resp.getDocument();
	}

	Document logoff(String token) {
		Response resp = dispatch.invoke(new Request(FogBugz.LOGOFF, util.map(FogBugz.TOKEN, token)));
		return resp.getDocument();
	}

	Document scout(String token, String project, String area, String title, String description) {
		Response resp = dispatch.invoke(new Request(FogBugz.NEW, util.map(FogBugz.TOKEN, token, //
				FogBugz.S_PROJECT, project, FogBugz.S_AREA, area, FogBugz.S_SCOUT_DESCRIPTION, title, //
				FogBugz.S_TITLE, title, FogBugz.S_EVENT, description)));
		return resp.getDocument();
	}

	Document scout(String token, String project, String area, String title, String description, List<Attachment> attachments) {
		Response resp = dispatch.invoke(new Request(FogBugz.NEW, util.map(FogBugz.TOKEN, token, //
				FogBugz.S_PROJECT, project, FogBugz.S_AREA, area, FogBugz.S_SCOUT_DESCRIPTION, title, //
				FogBugz.S_TITLE, title, FogBugz.S_EVENT, description)).attach(attachments));
		return resp.getDocument();
	}

	Document search(String token, String query) {
		List<String> cols = asList(FogBugz.S_TITLE, FogBugz.S_PROJECT, FogBugz.S_AREA, FogBugz.S_STATUS, FogBugz.S_EVENT, FogBugz.S_SCOUT_MESSAGE, FogBugz.S_SCOUT_DESCRIPTION);

		Response resp = dispatch.invoke(new Request(FogBugz.SEARCH, util.map(FogBugz.TOKEN, token, //
				FogBugz.QUERY, query, FogBugz.COLS, collectionToCommaDelimitedString(cols))));

		return resp.getDocument();
	}
}
