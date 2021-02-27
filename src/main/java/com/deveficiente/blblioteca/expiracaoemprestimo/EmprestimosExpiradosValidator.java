package com.deveficiente.blblioteca.expiracaoemprestimo;

import java.time.Clock;
import java.util.Objects;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.deveficiente.blblioteca.emprestimo.NovoEmprestimoRequest;
import com.deveficiente.blblioteca.novousuario.Usuario;

@Component
public class EmprestimosExpiradosValidator implements Validator {

	private EntityManager manager;
	private Clock relogio;

	public EmprestimosExpiradosValidator(EntityManager manager,Clock relogio) {
		super();
		this.manager = manager;
		this.relogio = relogio;		
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
		Usuario usuario = manager.find(Usuario.class, request.getIdUsuario());
		
		Assert.state(Objects.nonNull(usuario),"Usuario passado precisa existir no sistema");
		
		if(usuario.alcancouLimiteEmprestimosExpirados(relogio)) {
			errors.reject(null, "Você já tem mais emprestimos expirados que o permitido");
		}
	}

}
