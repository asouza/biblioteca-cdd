package com.deveficiente.blblioteca.emprestimo;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.deveficiente.blblioteca.novainstancia.Instancia;
import com.deveficiente.blblioteca.novainstancia.Tipo;
import com.deveficiente.blblioteca.novolivro.Livro;
import com.deveficiente.blblioteca.novousuario.TipoUsuario;
import com.deveficiente.blblioteca.novousuario.Usuario;

public class EmprestimoTest {

	@Test
	@DisplayName("deveria verificar que um emprestimo ainda não expirou")
	void teste1() throws Exception {
		Usuario usuario = new Usuario(TipoUsuario.PESQUISADOR);
		Livro livro = new Livro("titulo", BigDecimal.TEN, "984623786432");
		Instancia instanciaSelecionada = new Instancia(Tipo.LIVRE, livro);
		int tempo = 10;
		Emprestimo emprestimo = new Emprestimo(usuario, instanciaSelecionada,
				tempo);
		Assertions.assertFalse(emprestimo.expirado(Clock.systemUTC()));

	}
	
	@ParameterizedTest
	@CsvSource({
		"10",
		"11"
	})
	void teste2(int tempo) throws Exception {
		Usuario usuario = new Usuario(TipoUsuario.PESQUISADOR);
		Livro livro = new Livro("titulo", BigDecimal.TEN, "984623786432");
		Instancia instanciaSelecionada = new Instancia(Tipo.LIVRE, livro);
		Emprestimo emprestimo = new Emprestimo(usuario, instanciaSelecionada,
				tempo);
		Clock relogioFixo = Clock.fixed(Instant.now().plus(tempo+1, ChronoUnit.DAYS), ZoneOffset.systemDefault());
		Assertions.assertTrue(emprestimo.expirado(relogioFixo));
		
	}
}
