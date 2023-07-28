package edu.tech.authentication.api;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tech.authentication.api.dto.authentication.NormalAuthenticationRequestDTO;
import edu.tech.authentication.api.dto.authentication.Oauth2AuthenticationRequestDTO;
import edu.tech.authentication.api.response.Response;
import edu.tech.authentication.api.service.authentication.AuthenticationResolver;
import edu.tech.authentication.config.exception.IllegalRequestAuthenticationException;
import edu.tech.authentication.config.exception.InvalidAuthenticationException;
import edu.tech.authentication.config.service.jwt.JwtUtils;
import edu.tech.authentication.model.User;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationApi {
	@Autowired
	@Qualifier("normal")
	private AuthenticationResolver normalResolver;

	@PostMapping("/normal")
	public ResponseEntity<Response> normalAuthentication(@RequestBody NormalAuthenticationRequestDTO request) {
		Response response = Response.builder().responses(new HashMap<>()).build();
		try {
			String jwt = normalResolver.resolve(request);
			response.getResponses().put("Success Authentication!, JwtToken: ", jwt);
		} catch (IllegalRequestAuthenticationException e) {
			response.getResponses().put("IllegalRequestAuthenticationException :", e.getMessage());
		} catch (InvalidAuthenticationException e) {
			response.getResponses().put("IllegalRequestAuthenticationException :", e.getMessage());
		}

		return ResponseEntity.ok(response);
	}

	@PostMapping("/oauth2")
	public ResponseEntity<Response> oauthAuthentication(Oauth2AuthenticationRequestDTO request) {
		return ResponseEntity.ok(new Response());
	}
}
