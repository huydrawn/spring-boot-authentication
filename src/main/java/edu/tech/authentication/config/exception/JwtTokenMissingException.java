package edu.tech.authentication.config.exception;

import javax.naming.AuthenticationException;

public class JwtTokenMissingException extends AuthenticationException {
	public JwtTokenMissingException(String error) {
		super(error);
	}
}
