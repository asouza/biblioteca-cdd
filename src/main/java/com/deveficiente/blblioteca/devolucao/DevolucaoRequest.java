package com.deveficiente.blblioteca.devolucao;

import javax.validation.constraints.NotNull;

import com.deveficiente.blblioteca.compartilhado.ExistsId;
import com.deveficiente.blblioteca.emprestimo.Emprestimo;
import com.deveficiente.blblioteca.novousuario.Usuario;

public class DevolucaoRequest {

	@NotNull
	@ExistsId(domainClass = Emprestimo.class,fieldName = "id")
	public final Long idEmprestimo;
	@NotNull
	@ExistsId(domainClass = Usuario.class,fieldName = "id")
	public final Long idUsuario;

	public DevolucaoRequest(@NotNull Long idEmprestimo,
			@NotNull Long idUsuario) {
		super();
		this.idEmprestimo = idEmprestimo;
		this.idUsuario = idUsuario;
	}

}
