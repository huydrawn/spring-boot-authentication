package edu.tech.authentication.config.oauth2provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

public interface ProvideBuilder {
	final static ApplicationContext context = new AnnotationConfigApplicationContext(Provider.class);

	public static ClientRegistration build(String registerID) {

		return (ClientRegistration) context.getBean((registerID.toLowerCase()));
	}
}
