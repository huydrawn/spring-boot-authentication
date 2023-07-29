package edu.tech.authentication.api.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tech.authentication.api.dto.authentication.AuthenticationRequestDTO;
import edu.tech.authentication.config.exception.EmailNotSupportedAuthenticationException;
import edu.tech.authentication.config.exception.IllegalRequestAuthenticationException;
import edu.tech.authentication.config.exception.InvalidAccessTokenException;
import edu.tech.authentication.config.exception.InvalidAuthenticationException;
import edu.tech.authentication.config.service.jwt.JwtUtils;
import edu.tech.authentication.model.User;

@Service
public abstract class AuthenticationResolver {
	@Autowired
	private JwtUtils jwtUtils;

	public abstract User valid(AuthenticationRequestDTO request) throws IllegalRequestAuthenticationException,
			InvalidAuthenticationException, InvalidAccessTokenException, EmailNotSupportedAuthenticationException;

	public String resolve(AuthenticationRequestDTO request)
			throws InvalidAccessTokenException, IllegalRequestAuthenticationException, InvalidAuthenticationException,
			EmailNotSupportedAuthenticationException {

		User user = this.valid(request);
		
		return jwtUtils.gennerateJwtToken(user);

	}
}