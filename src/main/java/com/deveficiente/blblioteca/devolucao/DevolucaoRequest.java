package com.deveficiente.blblioteca.devolucao;

import javax.validation.constraints.NotNull;

public class DevolucaoRequest {

	@NotNull
	public final Long idEmprestimo;
	@NotNull
	public final Long idUsuario;

	public DevolucaoRequest(@NotNull Long idEmprestimo,
			@NotNull Long idUsuario) {
		super();
		this.idEmprestimo = idEmprestimo;
		this.idUsuario = idUsuario;
	}

}
