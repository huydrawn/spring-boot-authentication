package edu.tech.authentication.api.dto.register;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class RegisterRequestDTO {
	private String username;
	private String password;
	private String email;
}
