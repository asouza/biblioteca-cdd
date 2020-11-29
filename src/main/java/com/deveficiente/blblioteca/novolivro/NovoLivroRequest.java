package com.deveficiente.blblioteca.novolivro;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.ISBN.Type;
import org.springframework.util.Assert;

import com.deveficiente.blblioteca.compartilhado.UniqueValue;

public class NovoLivroRequest {

	@NotBlank
	private String titulo;
	@NotNull
	@Positive
	private BigDecimal preco;
	@NotBlank
	@ISBN(type = Type.ISBN_10)
	@UniqueValue(domainClass = Livro.class,fieldName = "isbn")
	private String isbn;

	public NovoLivroRequest(@NotBlank String titulo,
			@NotNull @Positive BigDecimal preco,
			@NotBlank @ISBN(type = Type.ISBN_10) String isbn) {
		super();
		this.titulo = titulo;
		this.preco = preco;
		this.isbn = isbn;
	}

	public Livro toModel() {
		return new Livro(titulo,preco,isbn);
	}

}
