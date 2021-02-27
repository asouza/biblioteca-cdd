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

import com.deveficiente.blblioteca.expiracaoemprestimo.EmprestimosExpiradosValidator;

@RestController
public class NovoEmprestimoController {

	@Autowired
	private EntityManager manager;
	@Autowired
	//1
	private VerificacaoBasicaEmprestimoValidator verificacaoBasicaEmprestimoValidator;
	@Autowired
	private EmprestimosExpiradosValidator emprestimosExpiradosValidator; 
	
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(verificacaoBasicaEmprestimoValidator,emprestimosExpiradosValidator);
	}

	@PostMapping(value = "/api/emprestimos")
	@Transactional
	//1
	public Long executa(@RequestBody @Valid NovoEmprestimoRequest request) {
			
		//1
		Emprestimo novo = request.toModel(manager);
		manager.persist(novo);
		return novo.getId();
	}
}
