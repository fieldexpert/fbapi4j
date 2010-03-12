package com.fieldexpert.fbapi4j;

import java.util.List;

public interface Session {

	void close();

	void close(Case bug);

	void create(Entity t);

	List<Case> query(String... criterion);

	<T extends Entity> T get(Class<T> clazz, Integer id);

	<T extends Entity> T get(Class<T> clazz, String name);

	<T extends Entity> List<T> findAll(Class<T> clazz);

	void edit(Case bug);

	void reactivate(Case bug);

	void reopen(Case bug);

	void resolve(Case bug);

	void scout(Case bug);

}
