package com.deveficiente.blblioteca;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NovoEmprestimoController {

	@Autowired
	private EntityManager manager;

	@PostMapping(value = "/api/emprestimos")
	@Transactional
	public Long executa(@RequestBody @Valid NovoEmprestimoRequest request) {
//		Classe novo = request.toModel();
//		manager.persist(novo);
//		return novo.getId();
		return 1l;
	}
}
