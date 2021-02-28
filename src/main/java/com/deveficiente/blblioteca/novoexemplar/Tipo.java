package com.deveficiente.blblioteca.novoexemplar;

import com.deveficiente.blblioteca.novousuario.TipoUsuario;
import com.deveficiente.blblioteca.novousuario.Usuario;

public enum Tipo {
	LIVRE {
		@Override
		boolean aceita(Usuario usuario) {
			return true;
		}
	},RESTRITO {
		@Override
		boolean aceita(Usuario usuario) {
			return usuario.tipo(TipoUsuario.PESQUISADOR);
		}
	};

	abstract boolean aceita(Usuario usuario);
}
