package com.fieldexpert.fbapi4j.dispatch;

public class DispatchException extends RuntimeException {

	private static final long serialVersionUID = -8314668549503091538L;

	public DispatchException(Throwable cause) {
		super(cause);
	}

	public DispatchException(String message) {
		super(message);
	}

	public DispatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public DispatchException(String code, String description) {
		this(code + " : " + description);
	}
}
