package edu.tech.authentication.config.exception;

public class InvalidAccessTokenException extends Exception {
	public InvalidAccessTokenException(String error) {
		super(error);
	}
}
