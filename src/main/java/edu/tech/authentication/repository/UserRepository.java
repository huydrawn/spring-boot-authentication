package edu.tech.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.tech.authentication.model.Account;
import edu.tech.authentication.model.User;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByAccount(Account account);

	public boolean existsByEmail(String email);

	public Optional<User> findByEmail(String email);
}
