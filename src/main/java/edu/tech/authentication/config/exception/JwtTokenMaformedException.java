package edu.tech.authentication.config.exception;

import javax.naming.AuthenticationException;

public class JwtTokenMaformedException extends AuthenticationException {
	public JwtTokenMaformedException(String error) {
		super(error);
	}
}
