package edu.tech.authentication.api.dto.authentication;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class NormalAuthenticationRequestDTO extends AuthenticationRequestDTO {
	private String username;
	private String password;
}
