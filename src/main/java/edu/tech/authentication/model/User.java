package edu.tech.authentication.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import edu.tech.authentication.model.priority.Priority;
import edu.tech.authentication.model.priority.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "t_user")

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String age;
	@Enumerated
	private Role role;
	@ElementCollection
	private List<Priority> priorities;
	private boolean isEmailVertification;
	@OneToOne(cascade = CascadeType.ALL, targetEntity = Account.class)
	@JsonManagedReference
	private Account account;
}
