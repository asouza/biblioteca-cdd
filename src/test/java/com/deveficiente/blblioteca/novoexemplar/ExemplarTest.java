package com.deveficiente.blblioteca.novoexemplar;

import java.math.BigDecimal;
import java.time.Instant;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.deveficiente.blblioteca.emprestimo.Emprestimo;
import com.deveficiente.blblioteca.novoexemplar.Exemplar;
import com.deveficiente.blblioteca.novoexemplar.Tipo;
import com.deveficiente.blblioteca.novolivro.Livro;
import com.deveficiente.blblioteca.novousuario.TipoUsuario;
import com.deveficiente.blblioteca.novousuario.Usuario;

public class ExemplarTest {

	@DisplayName("deveria estar disponível para emprestimo se nunca foi emprestada")
	@Test
	void teste1() throws Exception {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Exemplar instancia = new Exemplar(Tipo.LIVRE, livro);

		Assertions.assertTrue(instancia.disponivelParaEmprestimo());
	}

	@DisplayName("deveria estar disponível para emprestimo se todos emprestimos foram devolvidos")
	@Test
	void teste2() throws Exception {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Exemplar instancia = new Exemplar(Tipo.LIVRE, livro);
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		Emprestimo emprestimoCriado = instancia.criaEmprestimo(usuario, 40);
		// ainda nao foi implementado a devolucao do emprestimo
		ReflectionTestUtils.setField(emprestimoCriado, "instanteDevolucao",
				Instant.now());

		Assertions.assertTrue(instancia.disponivelParaEmprestimo());
	}

	@DisplayName("nao deveria estar disponivel se tem emprestimo que ainda nao foi devolvido")
	@Test
	void teste3() throws Exception {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Exemplar instancia = new Exemplar(Tipo.LIVRE, livro);
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		instancia.criaEmprestimo(usuario, 40);

		Assertions.assertFalse(instancia.disponivelParaEmprestimo());
	}

	@DisplayName("Caso todos emprestimos tenham sido devolvidos "
			+ " ou nunca tenha sido emprestada, "
			+ "a instancia deveria aceitar um usuario compativel")
	@Test
	void teste4() throws Exception {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Exemplar instancia = new Exemplar(Tipo.LIVRE, livro);
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);		

		Assertions.assertTrue(instancia.disponivel(usuario));
	}
	
	@DisplayName("Caso todos emprestimos tenham sido devolvidos "
			+ " ou nunca tenha sido emprestada, "
			+ "a instancia não deveria aceitar um usuario incompativel")
	@Test
	void teste5() throws Exception {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Exemplar instancia = new Exemplar(Tipo.RESTRITO, livro);
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);		
		
		Assertions.assertFalse(instancia.disponivel(usuario));
	}
	
	@DisplayName("Caso tenha emprestimo em aberto"
			+ "a instancia não deveria aceitar um usuario compativel")
	@Test
	void teste6() throws Exception {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Exemplar instancia = new Exemplar(Tipo.LIVRE, livro);
		

		
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		instancia.criaEmprestimo(usuario, 40);
		
		Assertions.assertFalse(instancia.disponivel(usuario));
	}

}
