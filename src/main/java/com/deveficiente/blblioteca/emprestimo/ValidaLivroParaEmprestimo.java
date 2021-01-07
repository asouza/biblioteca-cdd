package com.deveficiente.blblioteca.emprestimo;

import org.springframework.validation.Errors;

import com.deveficiente.blblioteca.novolivro.Livro;
import com.deveficiente.blblioteca.novousuario.Usuario;

//2
public class ValidaLivroParaEmprestimo {

	public void valida(Usuario usuario, Livro livro,Errors errors) {
		//1
		if(!livro.aceitaSerEmprestado(usuario)) {
			errors.reject(null, "Este usuario não pode pegar este livro");
		}
		
		//1
		if(!livro.estaDisponivelParaEmprestimo()) {
			errors.reject(null, "Este livro não está disponível para emprestimo");		
		}		
	}

}
