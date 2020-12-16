package com.deveficiente.blblioteca.novainstancia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.deveficiente.blblioteca.emprestimo.Emprestimo;
import com.deveficiente.blblioteca.novolivro.Livro;
import com.deveficiente.blblioteca.novousuario.Usuario;

@Entity
//3
public class Instancia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotNull Tipo tipo;
	//1
	@ManyToOne
	private @NotNull @Valid Livro livro;
	@OneToMany(mappedBy = "instanciaSelecionada")
	//1
	private List<Emprestimo> emprestimos = new ArrayList<>();

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

	public boolean disponivelParaEmprestimo() {
		//
		return this.emprestimos.isEmpty() 
				||
				//1
				this.emprestimos.stream().allMatch(emprestimo -> emprestimo.foiDevolvido());
	}

	

}
