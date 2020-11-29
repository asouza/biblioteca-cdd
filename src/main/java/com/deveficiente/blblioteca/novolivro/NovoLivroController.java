package com.deveficiente.blblioteca.novolivro;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NovoLivroController {
	
	@Autowired
	private EntityManager manager;

	@PostMapping(value = "/livros")
	@Transactional
	public Long executa(@RequestBody @Valid NovoLivroRequest request) {
		Livro novoLivro = request.toModel();
		manager.persist(novoLivro);
		return novoLivro.getId();
	}

}
