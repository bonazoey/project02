package com.mycompany.backendapi.database.exception;

public class NoAccountExistException extends RuntimeException {
	public NoAccountExistException() {
	}
	
	public NoAccountExistException(String message) {
		super(message);
	}
}
