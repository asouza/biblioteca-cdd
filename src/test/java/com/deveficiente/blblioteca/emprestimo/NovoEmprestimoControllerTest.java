package com.deveficiente.blblioteca.emprestimo;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.deveficiente.blblioteca.compartilhado.TesteApi;
import com.deveficiente.blblioteca.novainstancia.Tipo;
import com.deveficiente.blblioteca.novousuario.TipoUsuario;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.constraints.Size;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.api.constraints.Unique;
import net.jqwik.spring.JqwikSpringSupport;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
public class NovoEmprestimoControllerTest {

	@Autowired
	private TesteApi testeApi;

	//@DisplayName("exercita empréstimos válidos para usuários padroes")
	@Property(tries = 10)
	public void teste1(
			@ForAll @AlphaChars @StringLength(min = 1, max = 255) String titulo,
			@ForAll @BigRange(min = "1", max = "100") BigDecimal valor,
			@ForAll @Size(10) List<@NumericChars @Unique Character> listaDigitosIsbn,
			@ForAll @IntRange(min=1,max=5) int numeroInstancias,
			@ForAll @IntRange(min = 1,max = 60) int tempo)
			throws Exception {

		String isbn = listaDigitosIsbn.stream()
		.map(c -> c.toString()).collect(Collectors.joining());
		
		String idLivro = testeApi.criaLivro(titulo, valor,isbn).andReturn().getResponse().getContentAsString();
		String idUsuario = testeApi.criaUsuario(TipoUsuario.PADRAO).andReturn().getResponse().getContentAsString();
		
		for(int i = 0;i < numeroInstancias ;i++) {
			testeApi.criaInstancia(isbn, Tipo.LIVRE);
		}
		
		for(int i = 0;i < numeroInstancias ;i++) {
			testeApi.criaEmprestimo(Long.valueOf(idLivro),Long.valueOf(idUsuario),tempo)
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());			
		}

	}
}
