package edu.tech.authentication.api.service.authentication;

import org.springframework.stereotype.Service;

import edu.tech.authentication.api.dto.authentication.AuthenticationRequestDTO;
import edu.tech.authentication.api.dto.authentication.Oauth2AuthenticationRequestDTO;
import edu.tech.authentication.config.exception.IllegalRequestAuthenticationException;
import edu.tech.authentication.config.service.jwt.JwtUtils;
import edu.tech.authentication.model.User;

@Service(value = "oauth")
public class OauthAuthenticationResolve extends AuthenticationResolver {

	@Override
	public User valid(AuthenticationRequestDTO request) throws IllegalRequestAuthenticationException {
		if (!(request instanceof Oauth2AuthenticationRequestDTO)) {
			throw new IllegalRequestAuthenticationException(
					"Request is not instance of \"" + Oauth2AuthenticationRequestDTO.class + "\" ");
		}

		return null;

	}

}
