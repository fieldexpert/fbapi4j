package com.fieldexpert.fbapi4j;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

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
			throw new RuntimeException("The entity is missing the required annotation @EntityConfig");
		}
	}

	abstract T build(Map<String, String> data, Document doc);

	protected final Response execute(Request request) {
		return dispatch.invoke(request);
	}

	protected final T find(Request request) {
		Response resp = execute(request);
		Document doc = resp.getDocument();
		Map<String, String> map = util.data(doc, config.element()).get(0);
		return build(map, doc);
	}

	public List<T> findAll() {
		Response resp = execute(new Request(config.list(), util.map(Fbapi4j.TOKEN, token)));
		return list(resp);
	}

	public T findById(Integer id) {
		return find(new Request(config.single(), util.map(Fbapi4j.TOKEN, token, config.id(), id)));
	}

	public T findByName(String name) {
		return find(new Request(config.single(), util.map(Fbapi4j.TOKEN, token, config.name(), name)));
	}

	protected final List<T> list(List<Map<String, String>> results, Document doc) {
		List<T> objects = new ArrayList<T>();
		for (Map<String, String> data : results) {
			objects.add(build(data, doc));
		}
		return objects;
	}

	protected final List<T> list(Response response) {
		Document doc = response.getDocument();
		return list(util.data(doc, config.element()), doc);
	}

}
