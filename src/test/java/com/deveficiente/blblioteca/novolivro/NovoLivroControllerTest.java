package com.deveficiente.blblioteca.novolivro;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.CharRange;
import net.jqwik.api.constraints.IntRange;
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
	private MockMvc mvc;

	@Property(tries = 100)
	public void teste1(
			@ForAll @AlphaChars @StringLength(min = 1, max = 255) String titulo,
			@ForAll @BigRange(min = "1", max = "100") BigDecimal valor,
			@ForAll @Size(10) List<@NumericChars @Unique Character> isbn) throws Exception {
		
		

		String novoLivro = new ObjectMapper()
			.writeValueAsString(
					Map.of("titulo",titulo,
						   "preco",valor,
						   "isbn",isbn.stream().map(c -> c.toString()).collect(Collectors.joining())));
		
		System.out.println(novoLivro);
		
		mvc.perform(
				MockMvcRequestBuilders.post("/livros")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(novoLivro))
		.andExpect(
				MockMvcResultMatchers.status().is2xxSuccessful())
		.andDo(handler -> {
			System.out.println(handler.getResponse().getContentAsString());
		});
		
	}
}
