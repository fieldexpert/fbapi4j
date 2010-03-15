package com.fieldexpert.fbapi4j;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;
import com.fieldexpert.fbapi4j.dispatch.Request;
import com.fieldexpert.fbapi4j.dispatch.Response;

abstract class AbstractHandler<T extends Entity> implements Handler<T> {
	protected Dispatch dispatch;
	protected Util util;
	protected String token;
	private EntityConfig config;

	protected AbstractHandler(Dispatch dispatch, Util util, String token) {
		this.dispatch = dispatch;
		this.util = util;
		this.token = token;

		@SuppressWarnings("unchecked")
		Class<T> entity = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.config = entity.getAnnotation(EntityConfig.class);
		if (this.config == null) {
			throw new RuntimeException("The handler was not configured properly.");
		}
	}

	abstract T build(Map<String, String> data);

	public List<T> findAll() {
		Response resp = dispatch.invoke(new Request(config.list(), util.map(Fbapi4j.TOKEN, token)));
		List<Map<String, String>> results = util.data(resp.getDocument(), config.element());
		List<T> objects = new ArrayList<T>();
		for (Map<String, String> data : results) {
			objects.add(build(data));
		}
		return objects;
	}

	public T findById(Integer id) {
		Response resp = dispatch.invoke(new Request(config.single(), util.map(Fbapi4j.TOKEN, token, config.id(), id)));
		Map<String, String> map = util.data(resp.getDocument(), config.element()).get(0);
		return build(map);
	}

	public T findByName(String name) {
		Response resp = dispatch.invoke(new Request(config.single(), util.map(Fbapi4j.TOKEN, token, config.name(), name)));
		Map<String, String> map = util.data(resp.getDocument(), config.element()).get(0);
		return build(map);
	}

}
