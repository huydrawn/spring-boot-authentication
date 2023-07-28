package edu.tech.authentication.api;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tech.authentication.api.response.Response;
import edu.tech.authentication.api.service.emailVertification.EmailVertificationService;
import edu.tech.authentication.config.exception.EmailVertifycationException;
import edu.tech.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/vertify")
@RequiredArgsConstructor
public class EmailVertificationApi {
	private final EmailVertificationService emailVertificationService;

	@GetMapping("/{token}")
	public ResponseEntity<Response> vertify(@PathVariable("token") String token) {
		Response response = Response.builder().responses(new HashMap<>()).build();
		try {
			emailVertificationService.vertify(token);
			response.getResponses().put("Vertify :", "SuccessFully");
		} catch (EmailVertifycationException e) {
			response.getResponses().put(e.getClass() + "", e.getMessage());
		}

		return ResponseEntity.ok(response);
	}

	@PostMapping("/resend")
	public ResponseEntity<Response> reSend(@RequestBody String email) {
		Response response = Response.builder().responses(new HashMap<>()).build();

		try {
			emailVertificationService.sendMailVertification(email);
			response.getResponses().put("Resend :", "SuccessFully");
		} catch (EmailVertifycationException e) {
			response.getResponses().put(e.getClass() + "", e.getMessage());
		}
		return ResponseEntity.ok(response);
	}
}
