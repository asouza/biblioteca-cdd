package com.deveficiente.blblioteca.novainstancia;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
	@OrderBy("instanteEmprestimo asc")
	private SortedSet<Emprestimo> emprestimos = new TreeSet<>();
	
	@Version
	private int versao;
	
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

	public Emprestimo criaEmprestimo(@NotNull @Valid Usuario usuario,
			@Positive int tempo) {
		Assert.state(this.disponivelParaEmprestimo(),"Não criamos emprestimos de instancias que não estão disponíveis");
		//1
		Emprestimo emprestimo = new Emprestimo(usuario,this,tempo);
		boolean adicionou = this.emprestimos.add(emprestimo);
		
		Assert.state(adicionou,"Por algum motivo a adicao do emprestimo falhou para esta instancia. Será que rolou problema de concorrencia?");
		
		this.versao++;		
		return emprestimo;
	}

	

}
