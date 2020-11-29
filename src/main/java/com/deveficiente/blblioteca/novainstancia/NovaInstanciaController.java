package com.deveficiente.blblioteca.novainstancia;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deveficiente.blblioteca.novolivro.Livro;

@RestController
public class NovaInstanciaController {
	
	@Autowired
	private LivroRepository repository;
	@Autowired
	private EntityManager manager;

	@PostMapping(value = "/livro/{isbn}/instancias")
	@Transactional
	public ResponseEntity<?> executa(@PathVariable("isbn") String isbn,@RequestBody @Valid NovaInstanciaRequest request) {
		Optional<Livro> possivelLivro = repository.findByIsbn(isbn);
		if(possivelLivro.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Instancia novaInstancia = request.toModel(possivelLivro.get());
		manager.persist(novaInstancia);
		
		
		return ResponseEntity.ok(novaInstancia.getId());
	}

}
