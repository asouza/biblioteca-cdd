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
			//1
			@Valid @RequestBody DevolucaoRequest request) {

		//1
		Emprestimo emprestimo = manager.find(Emprestimo.class,
				request.idEmprestimo);
		//1
		Usuario usuario = manager.find(Usuario.class, request.idUsuario);

		//1
		if (!emprestimo.pertence(usuario)) {
			return ResponseEntity.notFound().build();
		}
		
		if(emprestimo.foiDevolvido()) {
			return ResponseEntity.badRequest().build();
		}

		emprestimo.devolve(usuario);

		return ResponseEntity.ok().build();
	}

}
