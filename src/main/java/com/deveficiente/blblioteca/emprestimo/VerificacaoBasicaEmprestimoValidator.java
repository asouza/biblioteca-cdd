package com.deveficiente.blblioteca.emprestimo;

import java.util.Objects;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.deveficiente.blblioteca.novolivro.Livro;
import com.deveficiente.blblioteca.novousuario.Usuario;

@Component
//7
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
		//1
		if (errors.hasErrors()) {
			return;
		}

		//1
		NovoEmprestimoRequest request = (NovoEmprestimoRequest) target;
		//1
		Usuario usuario = manager.find(Usuario.class,request.getIdUsuario());
		//1
		Livro livro = manager.find(Livro.class,request.getIdLivro());
		
		Assert.state(Objects.nonNull(usuario),"O usuario tem que ser diferente de nulo para fazer a validacao");
		Assert.state(Objects.nonNull(livro),"O livro tem que ser diferente de nulo para fazer a validacao");
		
		//1
		new ValicacaoEspecificaEmprestimoLivro().valida(livro,usuario,errors);
		
		//1
		if(!usuario.tempoEmprestimoValido(request)) {
			errors.reject(null, "Você precisa definir o tempo do emprestimo");
		}
		
		//1
		if(!usuario.aindaPodeSolicitarEmprestimo()) {
			errors.reject(null, "Você já está no limite de emprestimos. O limite atual é 5");
		}
		

	}

}
