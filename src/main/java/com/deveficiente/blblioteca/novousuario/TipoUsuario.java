package com.deveficiente.blblioteca.novousuario;

public enum TipoUsuario {

	PADRAO{

		@Override
		boolean aceitaTempoEmprestimo(PedidoEmprestimoComTempo pedido) {
			return pedido.temTempoEmprestimo();
		}

		@Override
		protected boolean aceitaNovoEmprestimo(
				long quantidadeEmprestimosNaoDevolvidos) {
			return quantidadeEmprestimosNaoDevolvidos < 5;
		}
		
	},PESQUISADOR {

		@Override
		boolean aceitaTempoEmprestimo(PedidoEmprestimoComTempo pedido) {
			return true;
		}

		@Override
		protected boolean aceitaNovoEmprestimo(
				long quantidadeEmprestimosNaoDevolvidos) {
			return true;
		}
		
	};

	abstract boolean aceitaTempoEmprestimo(
			PedidoEmprestimoComTempo pedido);

	protected abstract boolean aceitaNovoEmprestimo(
			long quantidadeEmprestimosNaoDevolvidos);
}
