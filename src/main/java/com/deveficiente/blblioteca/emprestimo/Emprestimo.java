package com.deveficiente.blblioteca.emprestimo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;

import com.deveficiente.blblioteca.novainstancia.Instancia;
import com.deveficiente.blblioteca.novousuario.Usuario;

@Entity
public class Emprestimo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private @NotNull @Valid Usuario usuario;
	@ManyToOne
	private @NotNull @Valid Instancia instanciaSelecionada;
	private @Positive int tempo;

	public Emprestimo(@NotNull @Valid Usuario usuario,
			@NotNull @Valid Instancia instanciaSelecionada, @Positive int tempo) {
				this.usuario = usuario;
				this.instanciaSelecionada = instanciaSelecionada;
				this.tempo = tempo;
	}

	public Long getId() {
		Assert.state(Objects.nonNull(id), "Será que você esqueceu de persistir o emprestimo");
		return id;
	}

}
