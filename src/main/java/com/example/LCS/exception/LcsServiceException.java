package com.example.LCS.exception;

public class LcsServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LcsServiceException() {
		super();
	}

	public LcsServiceException(String message) {
		super(message);
	}
}
