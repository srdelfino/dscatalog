package br.pro.delfino.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pro.delfino.dscatalog.entities.Categoria;
import br.pro.delfino.dscatalog.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository repository;
	
	@Transactional(readOnly = true)
	public List<Categoria> buscarTudo(){
		return repository.findAll();
	}
}
