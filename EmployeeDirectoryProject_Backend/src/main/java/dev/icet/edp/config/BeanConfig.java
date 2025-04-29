package dev.icet.edp.config;

import dev.icet.edp.validation.ValidationErrorsHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
	private final ApplicationContext applicationContext;

	@Bean
	public ModelMapper getModelMapper () {
		return new ModelMapper();
	}

	@Bean
	public Logger getLogger () {
		return LoggerFactory.getLogger("GlobalLogger");
	}

	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder () {
		return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 12);
	}

	@Bean
	public ValidationErrorsHelper getValidationErrorsHelper () {
		return new ValidationErrorsHelper();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void print () {
		System.out.println("""
			
			 _____           _  __   __
			|  __ \\         | | \\ \\ / /
			| |  \\/ ___   __| |  \\ V /  ___ _ __ ___
			| | __ / _ \\ / _` |  /   \\ / _ \\ '__/ _ \\
			| |_\\ \\ (_) | (_| | / /^\\ \\  __/ | | (_) |
			 \\____/\\___/ \\__,_| \\/   \\/\\___|_|  \\___/
			""");
	}
}
