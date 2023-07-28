package edu.tech.authentication.api;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

import edu.tech.authentication.api.dto.register.RegisterRequestDTO;
import edu.tech.authentication.api.response.Response;
import edu.tech.authentication.api.service.registeration.RegisterationResolve;
import edu.tech.authentication.config.exception.EmailVertifycationException;
import edu.tech.authentication.config.exception.ExistedRegisterException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegiserApi {
	private final RegisterationResolve registerationResolve;
	private final SpringResourceTemplateResolver resolver;
	@GetMapping
	public String test() {
		System.out.println(resolver.getSuffix());
		return "";
	}
	@PostMapping
	public ResponseEntity<Response> register(@RequestBody RegisterRequestDTO request) {
		Response response = Response.builder().responses(new HashMap<>()).build();
		try {
			registerationResolve.resolve(request);
			response.getResponses().put("Register : " , "Successfully");
		} catch (ExistedRegisterException e) {
			response.getResponses().put(e.getClass() + "", e.getMessage());
		} catch (EmailVertifycationException e) {
			response.getResponses().put(e.getClass() + "", e.getMessage());
		}
		return ResponseEntity.ok(response);
	}
}
