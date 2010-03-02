package com.fieldexpert.fbapi4j;

public class FogbugzException extends RuntimeException {

	private static final long serialVersionUID = -4270632565549343507L;

	public FogbugzException(Throwable cause) {
		super(cause);
	}

	public FogbugzException(String message) {
		super(message);
	}

	public FogbugzException(String message, Throwable cause) {
		super(message, cause);
	}

}
