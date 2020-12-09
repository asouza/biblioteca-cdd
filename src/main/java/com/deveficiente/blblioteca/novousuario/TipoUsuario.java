package com.deveficiente.blblioteca.novousuario;

import com.deveficiente.blblioteca.emprestimo.NovoEmprestimoRequest;

public enum TipoUsuario {

	PADRAO{

		@Override
		boolean aceitaTempoEmprestimo(NovoEmprestimoRequest request) {
			return request.temTempoEmprestimo();
		}
		
	},PESQUISADOR {

		@Override
		boolean aceitaTempoEmprestimo(NovoEmprestimoRequest request) {
			return true;
		}
		
	};

	abstract boolean aceitaTempoEmprestimo(NovoEmprestimoRequest request);
}
