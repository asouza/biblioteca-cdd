package com.deveficiente.blblioteca.compartilhado;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TesteApi {

	@Autowired
	private MockMvc mvc;

	/**
	 * 
	 * @param titulo titulo do livro
	 * @param valor valor do livro
	 * @param isbn isbn do livro
	 * @return
	 * @throws Exception
	 */
	public ResultActions criaLivro(String titulo, BigDecimal valor, String isbn) throws Exception {
		
		String novoLivro = new ObjectMapper()
				.writeValueAsString(
						Map.of("titulo",titulo,
							   "preco",valor,
							   "isbn",isbn));
		
		System.out.println(novoLivro);
		
		return mvc.perform(
				MockMvcRequestBuilders.post("/livros")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(novoLivro));		
	}
	
	
}
