package com.deveficiente.blblioteca.compartilhado;

import java.time.Clock;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ClockGeneratorProd {

	public Clock clock() {
		return Clock.systemUTC();
	}
}
