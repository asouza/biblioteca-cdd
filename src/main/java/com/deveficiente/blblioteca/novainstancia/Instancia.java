package com.deveficiente.blblioteca.novainstancia;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.deveficiente.blblioteca.novolivro.Livro;
import com.deveficiente.blblioteca.novousuario.Usuario;

@Entity
public class Instancia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotNull Tipo tipo;
	@ManyToOne
	private @NotNull @Valid Livro livro;

	@Deprecated
	public Instancia() {

	}
	
	public Instancia(@NotNull Tipo tipo, @NotNull @Valid Livro livro) {
		this.tipo = tipo;
		this.livro = livro;
	}
	
	public Long getId() {
		Assert.state(id!=null,"O id está nulo. Chamou o persist?");
		return id;
	}
	
	public boolean aceita(Usuario usuario) {
		return this.tipo.aceita(usuario);
	}
	
	

}
