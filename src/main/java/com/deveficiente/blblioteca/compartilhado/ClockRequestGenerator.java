package com.deveficiente.blblioteca.compartilhado;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@Profile("dev")
public class ClockRequestGenerator {

	@Bean
	@RequestScope
	public Clock clock(HttpServletRequest request) {
		String addDays = request.getParameter("addDays");
		if (addDays != null) {
			
			Instant instanteDesejado = Instant.now()
					.plus(Integer.parseInt(addDays), ChronoUnit.DAYS);
			
			return Clock.fixed(instanteDesejado,
					ZoneId.systemDefault());
		}

		return Clock.systemUTC();
	}	
}
