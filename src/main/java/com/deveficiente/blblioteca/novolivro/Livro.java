package com.deveficiente.blblioteca.novolivro;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.ISBN.Type;
import org.springframework.util.Assert;

@Entity
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotBlank String titulo;
	private @NotNull @Positive BigDecimal preco;
	@Column(unique = true)
	private @NotBlank @ISBN(type = Type.ISBN_10) String isbn;
	
	@Deprecated
	public Livro() {

	}

	public Livro(@NotBlank String titulo, @NotNull @Positive BigDecimal preco,
			@NotBlank @ISBN(type = Type.ISBN_10) String isbn) {
				this.titulo = titulo;
				this.preco = preco;
				this.isbn = isbn;
	}

	public Long getId() {
		Assert.state(id!=null,"Não rola chamar o getId do livro com o id nulo. Será que você já persistiu?");
		return id;
	}

}
