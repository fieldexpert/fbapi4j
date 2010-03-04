package com.fieldexpert.fbapi4j.session;

import com.fieldexpert.fbapi4j.Case;

public interface Session {

	void assign(Case bug);

	void close();

	void close(Case bug);

	void create(Case bug);

	void edit(Case bug);

	void reactivate(Case bug);

	void reopen(Case bug);

	void resolve(Case bug);

}
