package com.deveficiente.blblioteca.novousuario;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.deveficiente.blblioteca.compartilhado.TesteApi;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.constraints.Size;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.api.constraints.Unique;
import net.jqwik.spring.JqwikSpringSupport;

@SpringBootTest
@AutoConfigureMockMvc
public class NovoUsuarioControllerTest {

	@Autowired
	private TesteApi testeApi;

	@DisplayName("deveria criar novos usuário")
	@ParameterizedTest
	@CsvSource({
		"PADRAO","PESQUISADOR"
	})
	public void teste1(String tipoUsuario) throws Exception {

		ResultActions actions = testeApi.criaUsuario(TipoUsuario.valueOf(tipoUsuario));
		actions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

	}
}
