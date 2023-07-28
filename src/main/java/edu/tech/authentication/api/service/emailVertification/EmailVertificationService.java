package edu.tech.authentication.api.service.emailVertification;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import edu.tech.authentication.config.exception.EmailVertifycationException;
import edu.tech.authentication.model.User;
import edu.tech.authentication.repository.EmailVertificationRepository;
import edu.tech.authentication.repository.UserRepository;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableAsync
public class EmailVertificationService {
	private final EmailVertificationRepository emailVertificationRepository;
	private final TemplateEngine templateEngine;
	private final JavaMailSender javaMailSender;
	private final UserRepository userRepository;
	@Value("${server.port}")
	private String port;
	@Value("${server.domain}")
	private String domain;
	@Value("${spring.mail.username}")
	private String from;

	public void checkMailVertificationExists(String email) throws EmailVertifycationException {
		if (emailVertificationRepository.existsByEmail(email)) {
			throw new EmailVertifycationException("EmailVertifycation for this email have created");
		}
	}

	@Async
	public void sendMailVertification(String email) throws EmailVertifycationException {
		this.send(email);
	}

	private String generateVertificationToken() {
		return UUID.randomUUID().toString();
	}

	public void vertify(String token) throws EmailVertifycationException {
		// take user from database by email
		Optional<EmailVertification> emailVertification = emailVertificationRepository.findByToken(token);
		if (emailVertification.isEmpty()) {
			throw new EmailVertifycationException("Not Found EmailVertifycation Please Send again!");
		}
		User user = userRepository.findByEmail(emailVertification.get().getEmail()).get();

		// Set the status of isEmailVertification is true for user
		user.setEmailVertification(true);
		// Update the user
		userRepository.save(user);
	}

	private void send(String email) {
		// create a new email vertification for save in database
		EmailVertification emailVertification = EmailVertification.builder().email(email)
				.token(generateVertificationToken()).build();

		// Create Email
		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper mimeMessageHelper;

		try {
			mimeMessageHelper = new MimeMessageHelper(message, true);
			mimeMessageHelper.setPriority(1);
			mimeMessageHelper.setSubject("Email Vertification");
			mimeMessageHelper.setFrom(from);
			mimeMessageHelper.setTo(email);

			Context context = new Context();
			context.setVariable("name", email);
			context.setVariable("url", getURLVertification(emailVertification.getToken()));
			String text = templateEngine.process("emailVertification", context);
			MimeMultipart mimeMultipart = new MimeMultipart();

			BodyPart content = new MimeBodyPart();
			content.setContent(text, "text/html");

			mimeMultipart.addBodyPart(content);

			message.setContent(mimeMultipart);
			javaMailSender.send(message);

			// save the emailVertificationRepository if it have send

			emailVertificationRepository.save(emailVertification);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	private String getURLVertification(String token) {

		return "http://" + domain + ":" + port + "/api/vertify/" + token;
	}
}
