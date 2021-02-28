package com.deveficiente.blblioteca.devolucao;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.deveficiente.blblioteca.emprestimo.Emprestimo;
import com.deveficiente.blblioteca.novoexemplar.Tipo;
import com.deveficiente.blblioteca.novolivro.Livro;
import com.deveficiente.blblioteca.novousuario.TipoUsuario;
import com.deveficiente.blblioteca.novousuario.Usuario;

public class DevolucaoControllerTest {

	@Test
	@DisplayName("bloqueia devolucao feita por um usuario que não pegou o exemplar")
	void teste1() throws Exception {

		
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		ReflectionTestUtils.setField(usuario, "id", 1l);
		Livro livro = new Livro("titulo", new BigDecimal("10"), "9743298743");		
		livro.novoExemplar(Tipo.LIVRE);
		Emprestimo emprestimo = usuario.criaEmprestimo(livro, 10);		
		
		EntityManager manager = Mockito.mock(EntityManager.class);
		Mockito.when(manager.find(Emprestimo.class, 1l)).thenReturn(emprestimo);
		
		Usuario outroUsuario = new Usuario(TipoUsuario.PADRAO);
		ReflectionTestUtils.setField(outroUsuario, "id", 2l);
		
		Mockito.when(manager.find(Usuario.class, 2l)).thenReturn(outroUsuario);
		
		DevolucaoRequest request = new DevolucaoRequest(1l, 2l);
		
		DevolucaoController devolucaoController = new DevolucaoController(manager);
		ResponseEntity<?> resposta = devolucaoController.devolve(request);
		
		Assertions.assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
	}
	
	@Test
	@DisplayName("deveria liberar devolucao do exemplar")
	void teste2() throws Exception {
		
		
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		ReflectionTestUtils.setField(usuario, "id", 1l);
		Livro livro = new Livro("titulo", new BigDecimal("10"), "9743298743");		
		livro.novoExemplar(Tipo.LIVRE);
		Emprestimo emprestimo = usuario.criaEmprestimo(livro, 10);		
		
		EntityManager manager = Mockito.mock(EntityManager.class);
		Mockito.when(manager.find(Emprestimo.class, 1l)).thenReturn(emprestimo);
		
		Mockito.when(manager.find(Usuario.class, 1l)).thenReturn(usuario);
		
		DevolucaoRequest request = new DevolucaoRequest(1l, 1l);
		
		DevolucaoController devolucaoController = new DevolucaoController(manager);
		ResponseEntity<?> resposta = devolucaoController.devolve(request);
		
		Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
}
