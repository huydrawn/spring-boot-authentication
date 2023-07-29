package edu.tech.authentication.config.exception;

import javax.naming.AuthenticationException;

public class EmailNotSupportedAuthenticationException extends AuthenticationException {
	public EmailNotSupportedAuthenticationException(String error) {
		super(error);
	}
}
