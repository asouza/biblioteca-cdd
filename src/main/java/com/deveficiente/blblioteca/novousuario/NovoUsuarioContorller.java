package com.deveficiente.blblioteca.novousuario;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NovoUsuarioContorller {

	@Autowired
	private EntityManager manager;

	@PostMapping(value = "/api/usuarios")
	@Transactional
	public Long executa(@RequestBody @Valid NovoUsuarioRequest request) {
		Usuario novo = request.toModel();
		manager.persist(novo);
		return novo.getId();
	}

}
