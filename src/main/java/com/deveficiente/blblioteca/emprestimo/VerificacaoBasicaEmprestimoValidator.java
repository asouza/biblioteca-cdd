package com.deveficiente.blblioteca.emprestimo;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.deveficiente.blblioteca.novolivro.Livro;
import com.deveficiente.blblioteca.novousuario.Usuario;

@Component
public class VerificacaoBasicaEmprestimoValidator implements Validator {

	private EntityManager manager;

	public VerificacaoBasicaEmprestimoValidator(EntityManager manager) {
		super();
		this.manager = manager;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return NovoEmprestimoRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (errors.hasErrors()) {
			return;
		}

		NovoEmprestimoRequest request = (NovoEmprestimoRequest) target;
		Usuario usuario = manager.find(Usuario.class,request.getIdUsuario());
		Livro livro = manager.find(Livro.class,request.getIdLivro());
		
		Assert.state(usuario!=null,"O usuario tem que ser diferente de nulo para fazer a validacao");
		Assert.state(livro!=null,"O livro tem que ser diferente de nulo para fazer a validacao");
		
		if(!livro.aceitaSerEmprestado(usuario)) {
			errors.reject(null, "Este usuario não pode pegar este livro");
		}
		

	}

}
