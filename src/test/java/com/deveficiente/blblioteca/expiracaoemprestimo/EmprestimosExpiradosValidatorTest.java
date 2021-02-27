package com.deveficiente.blblioteca.expiracaoemprestimo;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.deveficiente.blblioteca.emprestimo.NovoEmprestimoRequest;
import com.deveficiente.blblioteca.novainstancia.Instancia;
import com.deveficiente.blblioteca.novainstancia.Tipo;
import com.deveficiente.blblioteca.novolivro.Livro;
import com.deveficiente.blblioteca.novousuario.TipoUsuario;
import com.deveficiente.blblioteca.novousuario.Usuario;

public class EmprestimosExpiradosValidatorTest {

	@Test
	@DisplayName("deveria parar caso algum teste já tenha falhado")
	void teste1() throws Exception {
		EntityManager manager = Mockito.mock(EntityManager.class);
		Clock relogio = Clock.systemUTC();

		EmprestimosExpiradosValidator validador = new EmprestimosExpiradosValidator(
				manager, relogio);

		Errors errors = new BeanPropertyBindingResult(new Object(), "object");
		errors.reject(null, "mensagem default");

		validador.validate(new Object(), errors);

		Assertions.assertEquals(1, errors.getErrorCount());
		Mockito.verify(manager,Mockito.never()).find(Usuario.class, 1l);
	}

	@Test
	@DisplayName("deveria falhar no caso de emprestimos expirados")
	void teste2() throws Exception {

	
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		ReflectionTestUtils.setField(usuario, "id", 1l);
		Livro livro = new Livro("titulo", new BigDecimal("10"), "9743298743");		
		livro.novaInstancia(Tipo.LIVRE);
		usuario.criaEmprestimo(livro, 10);

		EntityManager manager = Mockito.mock(EntityManager.class);
		Mockito.when(manager.find(Usuario.class, 1l)).thenReturn(usuario);

		Clock relogio = Clock.fixed(Instant.now().plus(11, ChronoUnit.DAYS),
				ZoneId.systemDefault());

		EmprestimosExpiradosValidator validador = new EmprestimosExpiradosValidator(
				manager, relogio);

		NovoEmprestimoRequest novoEmprestimoRequest = new NovoEmprestimoRequest(1l, 1l);
		Errors errors = new BeanPropertyBindingResult(novoEmprestimoRequest, "object");
		validador.validate(novoEmprestimoRequest, errors);

		Assertions.assertEquals(1, errors.getErrorCount());
		Mockito.verify(manager).find(Usuario.class, 1l);
	}
}
