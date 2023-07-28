package edu.tech.authentication.config.exception;

import javax.naming.AuthenticationException;

public class IllegalRequestAuthenticationException extends AuthenticationException {
	public IllegalRequestAuthenticationException(String error) {
		super(error);
	}
}
