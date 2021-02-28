package com.deveficiente.blblioteca.devolucao;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deveficiente.blblioteca.emprestimo.Emprestimo;
import com.deveficiente.blblioteca.novousuario.Usuario;

@RestController
public class DevolucaoController {

	private EntityManager manager;

	public DevolucaoController(EntityManager manager) {
		this.manager = manager;
	}

	@PostMapping(value = "/api/devolucoes")
	@Transactional
	public ResponseEntity<?> devolve(
			@Valid @RequestBody DevolucaoRequest request) {

		Emprestimo emprestimo = manager.find(Emprestimo.class,
				request.idEmprestimo);
		Usuario usuario = manager.find(Usuario.class, request.idUsuario);

		if (!emprestimo.pertence(usuario)) {
			return ResponseEntity.notFound().build();
		}

		emprestimo.devolve(usuario);

		return ResponseEntity.ok().build();
	}

}
