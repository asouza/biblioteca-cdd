package com.deveficiente.blblioteca;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NovoEmprestimoRequest {

	@NotNull
	@Positive
	private Long idUsuario;
	@NotNull
	@Positive
	private Long idLivro;
	private Integer tempo;

	public NovoEmprestimoRequest(@NotNull Long idUsuario,
			@NotNull Long idLivro) {
		super();
		this.idUsuario = idUsuario;
		this.idLivro = idLivro;
	}
	
	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

}
