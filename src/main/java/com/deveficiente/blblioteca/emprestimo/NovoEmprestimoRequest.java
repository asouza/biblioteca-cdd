package com.deveficiente.blblioteca.emprestimo;

import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;

import com.deveficiente.blblioteca.novousuario.PedidoEmprestimoComTempo;

//1
public class NovoEmprestimoRequest implements PedidoEmprestimoComTempo {

	@NotNull
	@Positive
	private Long idUsuario;
	@NotNull
	@Positive
	private Long idLivro;
	@Range(min = 1,max = 60)
	private Integer tempo;

	public NovoEmprestimoRequest(@NotNull Long idUsuario,
			@NotNull Long idLivro) {
		super();
		this.idUsuario = idUsuario;
		this.idLivro = idLivro;
	}
	
	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}
	
	public Long getIdLivro() {
		return idLivro;
	}

	public boolean temTempoEmprestimo() {
		return Optional.ofNullable(tempo).isPresent();
	}

}
