package com.fieldexpert.fbapi4j;

public class Fbapi4jException extends RuntimeException {

	private static final long serialVersionUID = -4270632565549343507L;

	public Fbapi4jException(Throwable cause) {
		super(cause);
	}

	public Fbapi4jException(String message) {
		super(message);
	}

	public Fbapi4jException(String message, Throwable cause) {
		super(message, cause);
	}

}
