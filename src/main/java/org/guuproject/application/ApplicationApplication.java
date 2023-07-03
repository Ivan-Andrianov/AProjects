package org.guuproject.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.net.PasswordAuthentication;

@SpringBootApplication
@EnableJpaRepositories
public class ApplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationApplication.class, args);
	}

}
