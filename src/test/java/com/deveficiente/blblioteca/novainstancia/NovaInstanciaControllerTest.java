package com.deveficiente.blblioteca.novainstancia;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.deveficiente.blblioteca.compartilhado.TesteApi;

@SpringBootTest
@AutoConfigureMockMvc
public class NovaInstanciaControllerTest {

	@Autowired
	private TesteApi testeApi;

	@DisplayName("Deveria criar novas instancias para determinado livro")
	@ParameterizedTest
	@CsvSource({
		"9754356732,LIVRE",
		"9754356733,RESTRITO",
	})
	public void teste1(String isbn,String tipo)
			throws Exception {

		testeApi.criaLivro("titulo", BigDecimal.ONE, isbn);
		
		ResultActions actions = testeApi.criaInstancia(isbn,Tipo.valueOf(tipo));
		actions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

	}
}
