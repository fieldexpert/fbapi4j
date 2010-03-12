package com.fieldexpert.fbapi4j;

import java.util.List;

interface Handler<T extends Entity> {

	void create(T t);

	List<T> findAll();

	T findById(Integer id);

	T findByName(String name);

}
