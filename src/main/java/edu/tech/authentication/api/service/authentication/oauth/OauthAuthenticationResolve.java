package edu.tech.authentication.api.service.authentication.oauth;

import java.time.Instant;
import java.util.Optional;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import edu.tech.authentication.api.dto.authentication.AuthenticationRequestDTO;
import edu.tech.authentication.api.dto.authentication.Oauth2AuthenticationRequestDTO;
import edu.tech.authentication.api.service.authentication.AuthenticationResolver;
import edu.tech.authentication.config.exception.EmailNotSupportedAuthenticationException;
import edu.tech.authentication.config.exception.IllegalRequestAuthenticationException;
import edu.tech.authentication.config.exception.InvalidAccessTokenException;
import edu.tech.authentication.config.oauth2provider.ProvideBuilder;
import edu.tech.authentication.model.Account;
import edu.tech.authentication.model.Oauth2Account;
import edu.tech.authentication.model.User;
import edu.tech.authentication.repository.AccountRepository;
import edu.tech.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service(value = "oauth2")
@RequiredArgsConstructor
public class OauthAuthenticationResolve extends AuthenticationResolver {
	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final Oauth2UserResolver oauth2UserResolver;

	@Override
	public User valid(AuthenticationRequestDTO request) throws IllegalRequestAuthenticationException,
			InvalidAccessTokenException, EmailNotSupportedAuthenticationException {

		if (!(request instanceof Oauth2AuthenticationRequestDTO)) {
			throw new IllegalRequestAuthenticationException(
					"Request is not instance of \"" + Oauth2AuthenticationRequestDTO.class + "\" ");
		}

		Oauth2AuthenticationRequestDTO requestDTO = (Oauth2AuthenticationRequestDTO) request;
		// Get Infomation about oauth2 user from accesstoken
		OAuth2User oAuth2User = this.processAccessToken(requestDTO);
		// check if have this email used yet?
		// if true check whetther Is it's account instance of Oauth2Account:
		// if true update this account
		Optional<User> userCheck = userRepository.findByEmail(oAuth2User.getAttribute("email"));
		// Create new object for handle
		User user = null;
		if (userCheck.isPresent()) {
			Account account = userCheck.get().getAccount();
			if (account instanceof Oauth2Account) {
				// Update Name of this account
				userCheck.get().setName(oAuth2User.getAttribute("name"));
				// Update in database
				userRepository.save(userCheck.get());
				// assgin to the user
				user = userCheck.get();
			} else {
				throw new EmailNotSupportedAuthenticationException(
						"This email have used for Other type of Account Can't use as Oauth2Acount");
			}
		}
		// if false we have save this account to database
		else {
			Account account = Oauth2Account.builder().registerID(requestDTO.getResigterID()).build();
			user = User.builder().email(oAuth2User.getAttribute("email")).name(oAuth2User.getAttribute("name"))
					.isEmailVertification(true).account(account).build();
			userRepository.save(user);
		}
		System.out.println(user);

		return user;

	}

	private OAuth2User processAccessToken(Oauth2AuthenticationRequestDTO requestDTO)
			throws InvalidAccessTokenException {
		OAuth2AccessToken accessToken = new OAuth2AccessToken(TokenType.BEARER, requestDTO.getAccessToken(),
				Instant.ofEpochMilli(requestDTO.getIssueAt()), Instant.ofEpochMilli(requestDTO.getExpriedAt()));

		ClientRegistration clientRegistration = ProvideBuilder.build(requestDTO.getResigterID());

		OAuth2UserRequest request = new OAuth2UserRequest(clientRegistration, accessToken);
		OAuth2User oAuth2User = null;
		try {
			oAuth2User = oauth2UserResolver.loadUser(request);
		} catch (OAuth2AuthenticationException e) {
			throw new InvalidAccessTokenException(e.getMessage());
		}

		return oAuth2User;
	}

}
