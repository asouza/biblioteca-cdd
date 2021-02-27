package com.deveficiente.blblioteca.emprestimo;

import java.util.Objects;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.deveficiente.blblioteca.novousuario.Usuario;

@Component
public class EmprestimosExpiradosValidator implements Validator {

	private EntityManager manager;

	public EmprestimosExpiradosValidator(EntityManager manager) {
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
		Usuario usuario = manager.find(Usuario.class, request.getIdUsuario());
		
		Assert.state(Objects.nonNull(usuario),"Usuario passado precisa existir no sistema");
		
		if(usuario.alcancouLimiteEmprestimosExpirados()) {
			errors.reject(null, "Você já tem mais emprestimos expirados que o permitido");
		}
	}

}
