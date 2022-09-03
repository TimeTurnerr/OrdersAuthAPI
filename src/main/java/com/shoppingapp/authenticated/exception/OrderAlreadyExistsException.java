package com.shoppingapp.authenticated.exception;

public class OrderAlreadyExistsException extends RuntimeException {

	public OrderAlreadyExistsException(String error) {
		super(error);
	}

}
