package com.deveficiente.blblioteca.emprestimo;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;
import org.springframework.util.Assert;

import com.deveficiente.blblioteca.novolivro.Livro;
import com.deveficiente.blblioteca.novousuario.PedidoEmprestimoComTempo;
import com.deveficiente.blblioteca.novousuario.Usuario;

//2
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
		return Objects.nonNull(tempo);
	}

	public Emprestimo toModel(EntityManager manager) {
		//1
		Livro livro = manager.find(Livro.class, idLivro);
		//1
		Usuario usuario = manager.find(Usuario.class,idUsuario);
		
		Assert.state(Objects.nonNull(livro),"O livro precisa existir para criar um emprestimo");
		Assert.state(Objects.nonNull(usuario),"O usuario precisa existir para criar um emprestimo");
		Assert.state(usuario.tempoEmprestimoValido(this),"Olha, você está tentando criar um emprestimo com um tempo não liberado para este usuario");
				
		int limiteMaximoDeTempoDeEmprestimo = 60;
		int tempoDefinido = Optional.ofNullable(tempo).orElse(limiteMaximoDeTempoDeEmprestimo);
		
		return usuario.criaEmprestimo(livro,tempoDefinido);
		
	}

}
