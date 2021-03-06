package com.deveficiente.blblioteca.novousuario;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;

import com.deveficiente.blblioteca.emprestimo.Emprestimo;
import com.deveficiente.blblioteca.novoexemplar.Exemplar;
import com.deveficiente.blblioteca.novolivro.Livro;

@Entity
//8 > 7
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// 1
	private @NotNull TipoUsuario tipo;
	@OneToMany(mappedBy = "usuario")
	// 1
	private List<Emprestimo> emprestimos = new ArrayList<>();

	@Deprecated
	public Usuario() {

	}

	public Usuario(@NotNull TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		Assert.state(Objects.nonNull(id),
				"O id não pode ser nulo neste ponto do código. Já chamou o persist do EntityManager?");
		return id;
	}

	public boolean tipo(TipoUsuario tipoBuscado) {
		return this.tipo.equals(tipoBuscado);
	}

	// 1
	public boolean tempoEmprestimoValido(PedidoEmprestimoComTempo pedido) {
		return tipo.aceitaTempoEmprestimo(pedido);
	}

	public boolean aindaPodeSolicitarEmprestimo() {
		// 1
		long quantidadeEmprestimosNaoDevolvidos = this.emprestimos.stream()
				.filter(emprestimo -> !emprestimo.foiDevolvido()).count();
		return this.tipo.aceitaNovoEmprestimo(quantidadeEmprestimosNaoDevolvidos);
	}

	// 3
	public Emprestimo criaEmprestimo(@NotNull @Valid Livro livro,
			@Positive int tempo) {
		Assert.state(livro.aceitaSerEmprestado(this),
				"Você está gerar um emprestimo de um livro que não aceita ser emprestado para o usuario "
						+ this.getId());
		Assert.state(livro.estaDisponivelParaEmprestimo(),
				"O livro precisa estar disponível para empréstimo para ser emprestado");
		Assert.state(this.aindaPodeSolicitarEmprestimo(),
				"Este usuário já está no limite de empréstimos");

		// 1
		Optional<Exemplar> possivelExemplaerSelecionado = livro
				.buscaInstanciaDisponivel(this);

		Assert.state(possivelExemplaerSelecionado.isPresent(),
				"Nesta altura do código a busca pela instância do livro deveria retornar alguma opção");

		Exemplar exemplarSelecionado = possivelExemplaerSelecionado.get();

		Assert.state(exemplarSelecionado.disponivelParaEmprestimo(),
				"A instancia retornada não está disponível, será que tem um bug no método #buscaInstanciaDisponivel ?");

		// 1
		Emprestimo novoEmprestimo = exemplarSelecionado.criaEmprestimo(this,
				tempo);
		this.emprestimos.add(novoEmprestimo);
		
		return novoEmprestimo;
	}

	public boolean alcancouLimiteEmprestimosExpirados(Clock relogio) {
		return this.emprestimos
				.stream()
				//1
				.anyMatch(emprestimo -> emprestimo.expirado(relogio));
	}

	public boolean mesmoId(Usuario outroUsuario) {
		Assert.state(Objects.nonNull(this.id),"O usuário precisa de id para essa operacao");
		Assert.notNull(outroUsuario.id, "O outro usuario precisa de id para essa operacao");
		//realmente este usuario não tem id natural, então criei este método
		//acho perigoso colocar o equals em cima de um id nao natural(vai ser nulo em determinado momento do tempo)
		return this.id.equals(outroUsuario.id);
	}

}
