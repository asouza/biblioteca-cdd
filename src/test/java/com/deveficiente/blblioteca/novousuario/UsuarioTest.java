package com.deveficiente.blblioteca.novousuario;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.util.ReflectionTestUtils;

import com.deveficiente.blblioteca.novainstancia.Tipo;
import com.deveficiente.blblioteca.novolivro.Livro;

public class UsuarioTest {

	@DisplayName("situacoes onde usuario pode ou não solicitar empréstimo")
	@ParameterizedTest
	@CsvSource({
		"4,true",
		"5,false",
		"2,true"		
	})
	void teste1(int numeroEmprestimos,boolean resultadoEsperado) throws Exception {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "897452386534");
		//cria instancias para serem emprestadas
		for(int i=0;i<6;i++) {
			livro.novaInstancia(Tipo.LIVRE);			
		}

		
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		ReflectionTestUtils.setField(usuario, "id", 1l);
		
		//cria 4 emprestimos, que é o valor mais próximo do limite.
		for(int i=0;i<numeroEmprestimos;i++) {
			usuario.criaEmprestimo(livro, 40);			
		}	
		
		Assertions.assertEquals(resultadoEsperado,usuario.aindaPodeSolicitarEmprestimo());
	}
}
