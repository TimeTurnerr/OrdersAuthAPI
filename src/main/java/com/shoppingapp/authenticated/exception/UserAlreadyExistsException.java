package com.shoppingapp.authenticated.exception;

public class UserAlreadyExistsException extends RuntimeException {

	public UserAlreadyExistsException(String error) {
		super(error);
	}

}
