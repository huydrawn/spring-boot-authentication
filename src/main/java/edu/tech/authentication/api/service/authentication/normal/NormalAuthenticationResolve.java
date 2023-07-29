package edu.tech.authentication.api.service.authentication.normal;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.tech.authentication.api.dto.authentication.AuthenticationRequestDTO;
import edu.tech.authentication.api.dto.authentication.NormalAuthenticationRequestDTO;
import edu.tech.authentication.api.service.authentication.AuthenticationResolver;
import edu.tech.authentication.config.exception.IllegalRequestAuthenticationException;
import edu.tech.authentication.config.exception.InvalidAuthenticationException;
import edu.tech.authentication.model.NormalAccount;
import edu.tech.authentication.model.User;
import edu.tech.authentication.repository.AccountRepository;
import edu.tech.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service(value = "normal")
@RequiredArgsConstructor
public class NormalAuthenticationResolve extends AuthenticationResolver {

	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public User valid(AuthenticationRequestDTO request)
			throws IllegalRequestAuthenticationException, InvalidAuthenticationException {
		if (!(request instanceof NormalAuthenticationRequestDTO)) {
			throw new IllegalRequestAuthenticationException(
					"Request is not instance of \"" + NormalAuthenticationRequestDTO.class + "\" ");
		}

		NormalAuthenticationRequestDTO requestDTO = (NormalAuthenticationRequestDTO) request;

		if (!StringUtils.hasText(requestDTO.getUsername())) {
			throw new IllegalRequestAuthenticationException("Username is empty");
		}
		if (!StringUtils.hasText(requestDTO.getUsername())) {
			throw new IllegalRequestAuthenticationException("Password is empty");
		}

		Optional<NormalAccount> account = accountRepository.findByUsername(requestDTO.getUsername());

		if (account.isEmpty()) {
			throw new InvalidAuthenticationException("UserName isn't exits");
		}
		if (!passwordEncoder.matches(requestDTO.getPassword(), account.get().getPassword())) {
			throw new InvalidAuthenticationException("Password not match");
		}
		Optional<User> user = userRepository.findByAccount(account.get());

		return user.get();

	}

}
