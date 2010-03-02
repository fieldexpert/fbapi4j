package com.fieldexpert.fbapi4j.dispatch;

import org.w3c.dom.Document;

public final class Response {

	private Document document;

	protected Response(Document document) {
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}
}
