package com.deveficiente.blblioteca.novoexemplar;

import javax.validation.constraints.NotNull;

import com.deveficiente.blblioteca.novolivro.Livro;

public class NovoExemplarRequest {

	@NotNull
	private Tipo tipo;
	
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Tipo getTipo() {
		return tipo;
	}

}
