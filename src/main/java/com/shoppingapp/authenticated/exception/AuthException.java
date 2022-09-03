package com.shoppingapp.authenticated.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class AuthException extends RuntimeException {

	public AuthException(String error) {
		super(error);
	}

}
