package com.example.LCS.exception;

public class LcsServiceValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LcsServiceValidationException() {
		super();
	}

	public LcsServiceValidationException(String message) {
		super(message);
	}
}
