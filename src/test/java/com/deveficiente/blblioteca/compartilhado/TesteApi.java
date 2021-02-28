package com.deveficiente.blblioteca.compartilhado;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.deveficiente.blblioteca.novoexemplar.Tipo;
import com.deveficiente.blblioteca.novousuario.TipoUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.jqwik.api.constraints.IntRange;

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
		
		String payload = new ObjectMapper()
				.writeValueAsString(
						Map.of("titulo",titulo,
							   "preco",valor,
							   "isbn",isbn));
		
		System.out.println(payload);
		
		return mvc.perform(
				MockMvcRequestBuilders.post("/livros")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(payload));		
	}

	public ResultActions criaUsuario(TipoUsuario tipoUsuario) throws Exception {
		String payload = new ObjectMapper()
				.writeValueAsString(
						Map.of("tipo",tipoUsuario));
		
		System.out.println(payload);
		
		return mvc.perform(
				MockMvcRequestBuilders.post("/api/usuarios")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(payload))
				.andDo(handler -> {
					System.out.println(handler.getResponse().getContentAsString());
				});
	}

	public ResultActions criaInstancia(String isbn, Tipo tipo) throws Exception {
		String payload = new ObjectMapper()
				.writeValueAsString(
						Map.of("tipo",tipo));
		
		System.out.println(payload);
		
		return mvc.perform(
				MockMvcRequestBuilders.post("/livro/{isbn}/instancias",isbn)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(payload));		
	}

	public ResultActions criaEmprestimo(long idUsuario, long idLivro,
			int tempo) throws Exception {
		String payload = new ObjectMapper()
				.writeValueAsString(
						Map.of("idUsuario",idUsuario,
							   "idLivro",idLivro,
							   "tempo",tempo));
		
		System.out.println(payload);
		
		return mvc.perform(
				MockMvcRequestBuilders.post("/api/emprestimos")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(payload));
	}
	
	
}
