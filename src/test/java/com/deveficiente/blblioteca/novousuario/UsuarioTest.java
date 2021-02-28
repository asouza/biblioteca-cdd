package com.deveficiente.blblioteca.novousuario;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.util.ReflectionTestUtils;

import com.deveficiente.blblioteca.emprestimo.Emprestimo;
import com.deveficiente.blblioteca.novoexemplar.Tipo;
import com.deveficiente.blblioteca.novolivro.Livro;

public class UsuarioTest {

	@DisplayName("situacoes onde usuario pode ou não solicitar empréstimo")
	@ParameterizedTest
	@CsvSource({
		"PADRAO,4,0,true",
		"PADRAO,5,0,false",
		"PADRAO,2,0,true",
		"PADRAO,5,1,true",
		"PESQUISADOR,4,0,true",
		"PESQUISADOR,5,0,true",
		"PESQUISADOR,15,0,true",
		"PESQUISADOR,2,0,true"
	})
	void teste1(String tipo,int numeroEmprestimos,int numeroDevolucoes,boolean resultadoEsperado) throws Exception {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "897452386534");
		//cria instancias para serem emprestadas
		for(int i=0;i<20;i++) {
			livro.novoExemplar(Tipo.LIVRE);			
		}

		
		Usuario usuario = new Usuario(TipoUsuario.valueOf(tipo));
		ReflectionTestUtils.setField(usuario, "id", 1l);
		
		//cria 4 emprestimos, que é o valor mais próximo do limite.
		ArrayList<Emprestimo> emprestimos = new ArrayList<>();
		for(int i=0;i<numeroEmprestimos;i++) {
			emprestimos.add(usuario.criaEmprestimo(livro, 40));			
		}
		
		for(int i=0;i<numeroDevolucoes;i++) {
			emprestimos.get(i).devolve(usuario);			
		}	
		
		
		
		Assertions.assertEquals(resultadoEsperado,usuario.aindaPodeSolicitarEmprestimo());
	}
	

}
