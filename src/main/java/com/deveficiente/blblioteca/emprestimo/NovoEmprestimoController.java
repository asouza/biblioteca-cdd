package com.deveficiente.blblioteca.emprestimo;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NovoEmprestimoController {

	@Autowired
	private EntityManager manager;
	@Autowired
	private VerificacaoBasicaEmprestimoValidator verificacaoBasicaEmprestimoValidator;
	
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(verificacaoBasicaEmprestimoValidator);
	}

	@PostMapping(value = "/api/emprestimos")
	@Transactional
	public Long executa(@RequestBody @Valid NovoEmprestimoRequest request) {
				
		Emprestimo novo = request.toModel(manager);
		System.out.println("executa");
		manager.persist(novo);
		return novo.getId();
	}
}
