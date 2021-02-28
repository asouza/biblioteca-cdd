package com.deveficiente.blblioteca.novolivro;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.ISBN.Type;
import org.springframework.util.Assert;

import com.deveficiente.blblioteca.novainstancia.Instancia;
import com.deveficiente.blblioteca.novainstancia.Tipo;
import com.deveficiente.blblioteca.novousuario.Usuario;

@Entity
//6
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotBlank String titulo;
	private @NotNull @Positive BigDecimal preco;
	@Column(unique = true)
	//@ISBN(type = Type.ISBN_10)
	private @NotBlank  String isbn;
	@OneToMany(mappedBy = "livro")
	//1
	private List<Instancia> instancias = new ArrayList<>();

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
		Assert.state(Objects.nonNull(this.id),
				"Não rola chamar o getId do livro com o id nulo. Será que você já persistiu?");
		return id;
	}

	//2 
	//1
	public boolean aceitaSerEmprestado(Usuario usuario) {
		//1
		return instancias.stream()
				.anyMatch(instancia -> instancia.aceita(usuario));
	}

	//1 lambda
	public boolean estaDisponivelParaEmprestimo() {//1
		return instancias.stream().anyMatch(instancia -> instancia.disponivelParaEmprestimo());
	}

	
	//1 Tipo
	public Instancia novaInstancia(Tipo tipo) {
		Instancia novaInstancia = new Instancia(tipo, this);
		this.instancias.add(novaInstancia);
		return novaInstancia;
	}

	//1 lambda
	public Optional<Instancia> buscaInstanciaDisponivel(Usuario usuario) {
		//1
		return instancias.stream()
			.filter(instancia -> instancia.disponivel(usuario))
			.findFirst();
	}

}
