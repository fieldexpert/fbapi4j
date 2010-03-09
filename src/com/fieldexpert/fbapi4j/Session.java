package com.fieldexpert.fbapi4j;

import java.io.Serializable;
import java.util.List;

public interface Session {

	void assign(Case bug);

	void close();

	void close(Case bug);

	void create(Case bug);

	void edit(Case bug);

	<T extends Entity> T get(Class<T> clazz, Serializable id);

	<T extends Entity> List<T> findAll(Class<T> clazz);

	void reactivate(Case bug);

	void reopen(Case bug);

	void resolve(Case bug);

	void scout(Case bug);

}
