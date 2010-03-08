package com.fieldexpert.fbapi4j.session;

import com.fieldexpert.fbapi4j.Case;
import com.fieldexpert.fbapi4j.Fbapi4jException;

class DisconnectedSession implements Session {

	public void assign(Case bug) {
		throw new IllegalStateException("No Session available.");
	}

	public void close() {
		throw new Fbapi4jException("The session has already been closed.");
	}

	public void close(Case bug) {
		throw new IllegalStateException("No Session available.");
	}

	public void create(Case bug) {
		throw new IllegalStateException("No Session available.");
	}

	public void edit(Case bug) {
		throw new IllegalStateException("No Session available.");
	}

	public void reactivate(Case bug) {
		throw new IllegalStateException("No Session available.");
	}

	public void reopen(Case bug) {
		throw new IllegalStateException("No Session available.");
	}

	public void resolve(Case bug) {
		throw new IllegalStateException("No Session available.");
	}

}
