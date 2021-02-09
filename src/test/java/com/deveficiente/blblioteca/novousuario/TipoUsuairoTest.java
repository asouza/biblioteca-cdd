package com.deveficiente.blblioteca.novousuario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TipoUsuairoTest {

	@DisplayName("situacoes onde tipo usuario padrão pode ou não solicitar empréstimo")
	@ParameterizedTest
	@CsvSource({
		"4,true",
		"2,true",
		"5,false",
		"6,false"
	})
	void teste1(long numeroEmprestimos,boolean resultadoEsperado) throws Exception {
		boolean aceite = TipoUsuario.PADRAO.aceitaNovoEmprestimo(numeroEmprestimos);
		Assertions.assertEquals(resultadoEsperado, aceite);
	}
}
