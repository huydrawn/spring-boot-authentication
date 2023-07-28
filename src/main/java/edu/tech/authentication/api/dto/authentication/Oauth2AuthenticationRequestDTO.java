package edu.tech.authentication.api.dto.authentication;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class Oauth2AuthenticationRequestDTO extends AuthenticationRequestDTO {
	private String resigterID;
	private String accessToken;
	private long issueAt;
	private long expriedAt;
}
