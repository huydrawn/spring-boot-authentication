package edu.tech.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.tech.authentication.api.service.emailVertification.EmailVertification;

@Component
public interface EmailVertificationRepository extends JpaRepository<EmailVertification, Integer> {
	public Optional<EmailVertification> findByToken(String token);

	public boolean existsByEmail(String email);
}
