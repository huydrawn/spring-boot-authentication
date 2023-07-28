package edu.tech.authentication.config.exception;

public class ExistedRegisterException extends Exception {
	public ExistedRegisterException(String error) {
		super(error);
	}
}
