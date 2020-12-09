package com.deveficiente.blblioteca.novousuario;

public enum TipoUsuario {

	PADRAO{

		@Override
		boolean aceitaTempoEmprestimo(PedidoEmprestimoComTempo pedido) {
			return pedido.temTempoEmprestimo();
		}
		
	},PESQUISADOR {

		@Override
		boolean aceitaTempoEmprestimo(PedidoEmprestimoComTempo pedido) {
			return true;
		}
		
	};

	abstract boolean aceitaTempoEmprestimo(
			PedidoEmprestimoComTempo pedido);
}
