package com.deveficiente.blblioteca.novainstancia;

import java.math.BigDecimal;
import java.time.Instant;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.deveficiente.blblioteca.emprestimo.Emprestimo;
import com.deveficiente.blblioteca.novolivro.Livro;
import com.deveficiente.blblioteca.novousuario.TipoUsuario;
import com.deveficiente.blblioteca.novousuario.Usuario;

public class InstanciaTest {

	@DisplayName("deveria estar disponível para emprestimo se nunca foi emprestada")
	@Test
	void teste1() throws Exception {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Instancia instancia = new Instancia(Tipo.LIVRE, livro);

		Assertions.assertTrue(instancia.disponivelParaEmprestimo());
	}
	
	@DisplayName("deveria estar disponível para emprestimo se todos emprestimos foram devolvidos")
	@Test
	void teste2() throws Exception {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Instancia instancia = new Instancia(Tipo.LIVRE, livro);
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		Emprestimo emprestimoCriado = instancia.criaEmprestimo(usuario, 40);
		//ainda nao foi implementado a devolucao do emprestimo
		ReflectionTestUtils.setField(emprestimoCriado, "instanteDevolucao", Instant.now());
		
		Assertions.assertTrue(instancia.disponivelParaEmprestimo());
	}
	
	@DisplayName("nao deveria estar disponivel se tem emprestimo que ainda nao foi devolvido")
	@Test
	void teste3() throws Exception {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Instancia instancia = new Instancia(Tipo.LIVRE, livro);
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		instancia.criaEmprestimo(usuario, 40);
		
		
		Assertions.assertFalse(instancia.disponivelParaEmprestimo());
	}
}
