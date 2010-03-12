package com.fieldexpert.fbapi4j;

import java.util.List;

class DisconnectedSession implements Session {

	public void close() {
		throw new Fbapi4jException("The session has already been closed.");
	}

	public void close(Case bug) {
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

	public void scout(Case bug) {
		throw new IllegalStateException("No Session available.");
	}

	public void create(Entity t) {
		throw new IllegalStateException("No Session available.");
	}

	public <T extends Entity> List<T> findAll(Class<T> clazz) {
		throw new IllegalStateException("No Session available.");
	}

	public <T extends Entity> T get(Class<T> clazz, Integer id) {
		throw new IllegalStateException("No Session available.");
	}

	public List<Case> query(String... criterion) {
		throw new IllegalStateException("No Session available.");
	}

	public <T extends Entity> T get(Class<T> clazz, String name) {
		throw new IllegalStateException("No Session available.");
	}
}
