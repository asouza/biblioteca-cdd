package com.deveficiente.blblioteca.novousuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.deveficiente.blblioteca.novolivro.Livro;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotNull TipoUsuario tipo;
	
	@Deprecated
	public Usuario() {

	}

	public Usuario(@NotNull TipoUsuario tipo) {
		this.tipo = tipo;
	}
	
	public Long getId() {
		Assert.state(id!=null,"O id não pode ser nulo neste ponto do código. Já chamou o persist do EntityManager?");
		return id;
	}

	public boolean padrao() {
		return this.tipo.equals(TipoUsuario.PADRAO);
	}

}
