package edu.tech.authentication.config.exception;

import javax.naming.AuthenticationException;

public class InvalidAuthenticationException extends AuthenticationException {
	public InvalidAuthenticationException(String error) {
		super(error);
	}
}
