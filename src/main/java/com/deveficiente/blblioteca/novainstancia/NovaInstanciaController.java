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
//5
public class NovaInstanciaController {
	
	@Autowired
	//1
	private LivroRepository repository;
	@Autowired
	private EntityManager manager;

	@PostMapping(value = "/livro/{isbn}/instancias")
	@Transactional
	//1 NovaInstanciaRequest
	public ResponseEntity<?> executa(@PathVariable("isbn") String isbn,@RequestBody @Valid NovaInstanciaRequest request) {
		//1
		Optional<Livro> possivelLivro = repository.findByIsbn(isbn);
		//1
		return possivelLivro.map(livro -> {
			//1
			Instancia novaInstancia = livro.novaInstancia(request.getTipo());
			
			manager.persist(novaInstancia);
			
			return ResponseEntity.ok(novaInstancia.getId());			
		}).orElse(ResponseEntity.notFound().build());
		
	}

}
