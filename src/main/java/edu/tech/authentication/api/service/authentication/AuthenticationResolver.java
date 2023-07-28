package edu.tech.authentication.api.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tech.authentication.api.dto.authentication.AuthenticationRequestDTO;
import edu.tech.authentication.config.exception.IllegalRequestAuthenticationException;
import edu.tech.authentication.config.exception.InvalidAuthenticationException;
import edu.tech.authentication.config.service.jwt.JwtUtils;
import edu.tech.authentication.model.User;

@Service
public abstract class AuthenticationResolver {
	@Autowired
	private JwtUtils jwtUtils;

	public abstract User valid(AuthenticationRequestDTO request)
			throws IllegalRequestAuthenticationException, InvalidAuthenticationException;

	public String resolve(AuthenticationRequestDTO request)
			throws IllegalRequestAuthenticationException, InvalidAuthenticationException {
		User user;
		try {
			user = this.valid(request);
			return jwtUtils.gennerateJwtToken(user);
		} catch (IllegalRequestAuthenticationException e) {
			// TODO Auto-generated catch block
			throw new IllegalRequestAuthenticationException(e.getMessage());
		} catch (InvalidAuthenticationException e) {
			// TODO Auto-generated catch block
			throw new InvalidAuthenticationException(e.getMessage());
		}

	}
}