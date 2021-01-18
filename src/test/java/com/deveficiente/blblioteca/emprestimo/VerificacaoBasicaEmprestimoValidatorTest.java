package com.deveficiente.blblioteca.emprestimo;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.deveficiente.blblioteca.novainstancia.Tipo;
import com.deveficiente.blblioteca.novolivro.Livro;
import com.deveficiente.blblioteca.novousuario.TipoUsuario;
import com.deveficiente.blblioteca.novousuario.Usuario;

public class VerificacaoBasicaEmprestimoValidatorTest {

	@DisplayName("deveria interromper a validação já exista um erro anterior")
	@Test
	void teste1() throws Exception {
		EntityManager manager = Mockito.mock(EntityManager.class);
		VerificacaoBasicaEmprestimoValidator validador = new VerificacaoBasicaEmprestimoValidator(
				manager);

		Object target = new Object();
		Errors errors = new BeanPropertyBindingResult(target, "target");
		errors.reject("codigoErro");

		validador.validate(target, errors);

		Assertions.assertEquals(1, errors.getErrorCount());

	}

	@DisplayName("deveria garantir que todo usuario regular definiu o tempo de emprestimo")
	@Test
	void teste2() throws Exception {
		EntityManager manager = Mockito.mock(EntityManager.class);
		VerificacaoBasicaEmprestimoValidator validador = new VerificacaoBasicaEmprestimoValidator(
				manager);

		Livro livro = new Livro("titulo", BigDecimal.TEN, "897452386534");
		livro.novaInstancia(Tipo.LIVRE);
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);

		Mockito.when(manager.find(Usuario.class, 1l)).thenReturn(usuario);
		Mockito.when(manager.find(Livro.class, 1l)).thenReturn(livro);

		NovoEmprestimoRequest request = new NovoEmprestimoRequest(1l, 1l);
		Errors errors = new BeanPropertyBindingResult(request, "target");

		validador.validate(request, errors);

		Assertions.assertEquals(1, errors.getErrorCount());
		Assertions.assertEquals("Você precisa definir o tempo do emprestimo",
				errors.getGlobalErrors().get(0).getDefaultMessage());

	}
	
	@DisplayName("deveria validar o número máximo de emprestimos de um determinado usuário")
	@Test
	void teste3() throws Exception {
		EntityManager manager = Mockito.mock(EntityManager.class);
		VerificacaoBasicaEmprestimoValidator validador = new VerificacaoBasicaEmprestimoValidator(
				manager);
		
		Livro livro = new Livro("titulo", BigDecimal.TEN, "897452386534");
		//cria instancias para serem emprestadas
		for(int i=0;i<6;i++) {
			livro.novaInstancia(Tipo.LIVRE);			
		}

		
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		ReflectionTestUtils.setField(usuario, "id", 1l);
		
		//cria o máximo de empréstimos permitidos neste momento, 5.
		for(int i=0;i<5;i++) {
			usuario.criaEmprestimo(livro, 40);			
		}
		
		
		//ReflectionTestUtils.setField(usuario, "emprestimos", emprestimosCriados);
		
		Mockito.when(manager.find(Usuario.class, 1l)).thenReturn(usuario);
		Mockito.when(manager.find(Livro.class, 1l)).thenReturn(livro);
		
		NovoEmprestimoRequest request = new NovoEmprestimoRequest(1l, 1l);
		request.setTempo(40);
		
		Errors errors = new BeanPropertyBindingResult(request, "target");
		
		validador.validate(request, errors);
		
		Assertions.assertEquals(1, errors.getErrorCount());
		Assertions.assertEquals("Você já está no limite de emprestimos. O limite atual é 5",
				errors.getGlobalErrors().get(0).getDefaultMessage());
		
	}
}
