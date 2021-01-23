package com.deveficiente.blblioteca.novolivro;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.deveficiente.blblioteca.compartilhado.TesteApi;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.constraints.Size;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.api.constraints.Unique;
import net.jqwik.spring.JqwikSpringSupport;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
public class NovoLivroControllerTest {

	@Autowired
	private TesteApi testeApi;

	@Property(tries = 100)
	public void teste1(
			@ForAll @AlphaChars @StringLength(min = 1, max = 255) String titulo,
			@ForAll @BigRange(min = "1", max = "100") BigDecimal valor,
			@ForAll @Size(10) List<@NumericChars @Unique Character> isbn)
			throws Exception {

		ResultActions actions = testeApi.criaLivro(titulo, valor, isbn.stream()
				.map(c -> c.toString()).collect(Collectors.joining()));
		actions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

	}
}
