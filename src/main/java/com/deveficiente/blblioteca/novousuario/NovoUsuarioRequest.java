package com.deveficiente.blblioteca.novousuario;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

public class NovoUsuarioRequest {

	@NotNull
	private TipoUsuario tipo;


	@JsonCreator(mode = Mode.PROPERTIES)
	public NovoUsuarioRequest(@NotNull TipoUsuario tipo) {
		this.tipo = tipo;
	}


	public Usuario toModel() {
		return new Usuario(tipo);
	}
	
	
}
