package edu.tech.authentication.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@DiscriminatorValue(value = "normal")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormalAccount extends Account {
	private String username;
	private String password;
}
