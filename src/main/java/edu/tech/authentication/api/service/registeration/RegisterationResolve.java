package edu.tech.authentication.api.service.registeration;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.tech.authentication.api.dto.register.RegisterRequestDTO;
import edu.tech.authentication.api.service.emailVertification.EmailVertificationService;
import edu.tech.authentication.config.exception.EmailVertifycationException;
import edu.tech.authentication.config.exception.ExistedRegisterException;
import edu.tech.authentication.model.Account;
import edu.tech.authentication.model.NormalAccount;
import edu.tech.authentication.model.User;
import edu.tech.authentication.repository.AccountRepository;
import edu.tech.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableAsync
public class RegisterationResolve {
	private final UserRepository userRepository;
	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailVertificationService emailVertificationService;

	public void resolve(RegisterRequestDTO request) throws ExistedRegisterException, EmailVertifycationException {
		// check whether Email exsist
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new ExistedRegisterException("Email has existed");
		}
		// check whether Username exsist
		if (accountRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new ExistedRegisterException("Username has existed");
		}
		// create account to save
		NormalAccount account = NormalAccount.builder().username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword())).build();
		// create user to save
		// above account is injected
		User user = User.builder().account(account).email(request.getEmail()).build();

		// check if we have had a EmailVertifycation of this email
		emailVertificationService.checkMailVertificationExists(request.getEmail());

		// send Email Vertification for User
		emailVertificationService.sendMailVertification(request.getEmail());
		// Save user
		userRepository.save(user);

	}
}
