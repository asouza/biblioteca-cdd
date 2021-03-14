package com.deveficiente.blblioteca.emprestimo;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

import com.deveficiente.blblioteca.novoexemplar.Exemplar;
import com.deveficiente.blblioteca.novousuario.Usuario;

@Entity
public class Emprestimo implements Comparable<Emprestimo> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	// 1
	private @NotNull @Valid Usuario usuario;
	@ManyToOne
	// 1
	private @NotNull @Valid Exemplar exemplarSelecionado;
	private @Positive int tempo;
	private Instant instanteDevolucao;
	private Instant instanteEmprestimo = Instant.now();;

	@Deprecated
	public Emprestimo() {
	}

	public Emprestimo(@NotNull @Valid Usuario usuario,
			@NotNull @Valid Exemplar exemplarSelecionado, @Positive int tempo) {
		Assert.isTrue(exemplarSelecionado.aceita(usuario),
				"Olha, você está construindo um emprestimo com instancia que nao aceita o usuario. Será que você verificou corretamente antes?");
		this.usuario = usuario;
		this.exemplarSelecionado = exemplarSelecionado;
		this.tempo = tempo;
	}

	public boolean expirado(Clock relogio) {
		LocalDate diaEsperadoRetorno = LocalDate
				.ofInstant(this.instanteEmprestimo, relogio.getZone())
				.plus(tempo, ChronoUnit.DAYS);
		
		return diaEsperadoRetorno
				.isBefore(LocalDate.now(relogio));
	}

	public Long getId() {
		Assert.state(Objects.nonNull(id),
				"Será que você esqueceu de persistir o emprestimo");
		return id;
	}

	public boolean foiDevolvido() {
		return Objects.nonNull(instanteDevolucao);
	}

	@Override
	public int compareTo(Emprestimo outro) {
		return this.instanteEmprestimo.compareTo(outro.instanteEmprestimo);
	}

	public boolean pertence(Usuario outroUsuario) {
		return this.usuario.mesmoId(outroUsuario);
	}

	/**
	 * 
	 * @param usuarioQueEstaDevolvendo usuario fazendo devolucao
	 */
	public void devolve(Usuario usuarioQueEstaDevolvendo) {
		Assert.state(this.pertence(usuarioQueEstaDevolvendo),
				"A pessoa devolvendo deve ser a mesma que pegou o exemplar");
		Assert.state(Objects.isNull(this.instanteDevolucao),
				"Um exemplar que já foi devolvido não pode ser devolvido de novo");

		this.instanteDevolucao = Instant.now();
	}

}
