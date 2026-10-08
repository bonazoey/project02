package com.mycompany.backendapi.database.exception;

public class DuplicatedMidException extends RuntimeException {
	public DuplicatedMidException() {
	}
	
	public DuplicatedMidException(String message) {
		super(message);
	}
}
