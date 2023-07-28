package edu.tech.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.tech.authentication.model.Account;
import edu.tech.authentication.model.NormalAccount;
@Component
public interface AccountRepository extends JpaRepository<Account, Integer> {
	public Optional<NormalAccount> findByUsernameAndPassword(String username, String password);
	public Optional<NormalAccount> findByUsername(String username);
	
}
