package com.fieldexpert.fbapi4j;

public interface Session {

	void assign(Case bug);

	void close();

	void close(Case bug);

	void create(Case bug);

	void edit(Case bug);

	Case getCase(int number);

	void reactivate(Case bug);

	void reopen(Case bug);

	void resolve(Case bug);

	void scout(Case bug);

}
