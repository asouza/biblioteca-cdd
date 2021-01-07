package com.deveficiente.blblioteca.emprestimo;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.deveficiente.blblioteca.novainstancia.Tipo;
import com.deveficiente.blblioteca.novolivro.Livro;
import com.deveficiente.blblioteca.novousuario.TipoUsuario;
import com.deveficiente.blblioteca.novousuario.Usuario;

public class ValicacaoEspecificaEmprestimoLivroTest {

	@Test
	@DisplayName("rejeita o emprestimo caso o livro não possa ser emprestado para o usuario")
	void teste1() throws Exception {
		ValicacaoEspecificaEmprestimoLivro validador = 
				new ValicacaoEspecificaEmprestimoLivro();
		
		Livro livro = new Livro("titulo", BigDecimal.TEN, "897452386534");
		livro.novaInstancia(Tipo.RESTRITO);
		
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);	
		
		Errors errors = Mockito.spy(new BeanPropertyBindingResult(new Object(), "target"));
		
		validador.valida(livro , usuario, errors);
		
		Assertions.assertEquals(1, errors.getErrorCount());
		Mockito.verify(errors, Mockito.never()).reject(null, "Este livro não está disponível para emprestimo");
	}
	
	@Test
	@DisplayName("rejeita o emprestimo caso o livro nao esteja disponivel para emprestimo")
	void teste2() throws Exception {
		ValicacaoEspecificaEmprestimoLivro validador = 
				new ValicacaoEspecificaEmprestimoLivro();
		
		Livro livro = new Livro("titulo", BigDecimal.TEN, "897452386534");		
		livro.novaInstancia(Tipo.LIVRE);		
		
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		
		ReflectionTestUtils.setField(usuario, "id", 1l);
		
		livro.criaEmprestimo(usuario, 60);
		
		Errors errors = Mockito.spy(new BeanPropertyBindingResult(new Object(), "target"));
		
		validador.valida(livro , usuario, errors);
		
		Assertions.assertEquals(1, errors.getErrorCount());
		Mockito.verify(errors,Mockito.never()).reject(null, "Este usuario não pode pegar este livro");
	}
}
