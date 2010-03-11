package com.fieldexpert.fbapi4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import com.fieldexpert.fbapi4j.common.Assert;
import com.fieldexpert.fbapi4j.common.Util;
import com.fieldexpert.fbapi4j.dispatch.Dispatch;
import com.fieldexpert.fbapi4j.dispatch.Request;
import com.fieldexpert.fbapi4j.dispatch.Response;
import com.fieldexpert.fbapi4j.http.Http;

class ConnectedSession implements Session {
	private String token;
	private String url;
	private Dispatch dispatch;
	private Util util;

	private CaseHandler caseHandler;
	private AreaHandler areaHandler;

	private Map<Class<? extends Entity>, Handler<? extends Entity>> handlers = new HashMap<Class<? extends Entity>, Handler<? extends Entity>>();

	ConnectedSession(Dispatch dispatch) {
		this.dispatch = dispatch;
		this.util = new Util();
	}

	private void initHandlers() {
		handlers.put(Area.class, areaHandler = new AreaHandler(dispatch, util, token));
		handlers.put(Case.class, caseHandler = new CaseHandler(dispatch, util, token));
		handlers.put(Person.class, new PersonHandler(dispatch, util, token));
		handlers.put(Priority.class, new PriorityHandler(dispatch, util, token));
		handlers.put(Project.class, new ProjectHandler(dispatch, util, token));
	}

	private void api() {
		if (url == null) {
			Response response = dispatch.invoke(Http.GET, util.url(dispatch.getEndpoint(), Fbapi4j.API_XML));
			Document doc = response.getDocument();
			url = util.children(doc).get("url");
			dispatch.setProperty("path", url);
		}
	}

	public void close() {
		Assert.notNull(token);
		dispatch.invoke(new Request(Fbapi4j.LOGOFF, util.map(Fbapi4j.TOKEN, token)));
		token = null;
	}

	public void close(Case bug) {
		caseHandler.close(bug);
	}

	public void edit(Case bug) {
		caseHandler.edit(bug);
	}

	void logon() {
		api();
		Response resp = dispatch.invoke(new Request(Fbapi4j.LOGON, util.map(Fbapi4j.EMAIL, dispatch.getEmail(), Fbapi4j.PASSWORD, dispatch.getPassword())));
		Document doc = resp.getDocument();
		token = util.children(doc).get("token");
		initHandlers();
	}

	public void reactivate(Case bug) {
		caseHandler.reactivate(bug);
	}

	public void reopen(Case bug) {
		caseHandler.reopen(bug);
	}

	public void resolve(Case bug) {
		caseHandler.resolve(bug);
	}

	public void scout(Case bug) {
		caseHandler.scout(bug);
	}

	@SuppressWarnings("unchecked")
	public void create(Entity t) {
		Handler<Entity> handler = (Handler<Entity>) getHandler(t.getClass());
		handler.create(t);
	}

	public <T extends Entity> T get(Class<T> clazz, Integer id) {
		return (T) getHandler(clazz).findById(id);
	}

	public <T extends Entity> List<T> findAll(Class<T> clazz) {
		return getHandler(clazz).findAll();
	}

	@SuppressWarnings("unchecked")
	private <T extends Entity> Handler<T> getHandler(Class<T> clazz) {
		Handler h = handlers.get(clazz);
		if (h == null) {
			throw new IllegalStateException();
		}
		return h;
	}

	public List<Case> query(String... criterion) {
		return caseHandler.query(criterion);
	}

	List<Area> findAreasByProjectId(Integer id) {
		return areaHandler.getByProject(id);
	}
}
