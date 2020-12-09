package com.deveficiente.blblioteca.novousuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.deveficiente.blblioteca.emprestimo.NovoEmprestimoRequest;
import com.deveficiente.blblioteca.novolivro.Livro;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//1
	private @NotNull TipoUsuario tipo;
	
	@Deprecated
	public Usuario() {

	}

	public Usuario(@NotNull TipoUsuario tipo) {
		this.tipo = tipo;
	}
	
	public Long getId() {
		Assert.state(id!=null,"O id não pode ser nulo neste ponto do código. Já chamou o persist do EntityManager?");
		return id;
	}

	public boolean tipo(TipoUsuario tipoBuscado) {
		return this.tipo.equals(tipoBuscado);
	}

	//1
	public boolean tempoEmprestimoValido(PedidoEmprestimoComTempo pedido) {
		return tipo.aceitaTempoEmprestimo(pedido);
	}

}
