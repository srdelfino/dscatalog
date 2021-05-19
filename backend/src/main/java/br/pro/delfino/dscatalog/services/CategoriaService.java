package br.pro.delfino.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pro.delfino.dscatalog.dto.CategoriaDTO;
import br.pro.delfino.dscatalog.entities.Categoria;
import br.pro.delfino.dscatalog.repositories.CategoriaRepository;
import br.pro.delfino.dscatalog.services.exceptions.EntidadeNaoEncontradaException;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository repository;

	@Transactional(readOnly = true)
	public List<CategoriaDTO> buscarTudo() {
		List<Categoria> categorias = repository.findAll();

		List<CategoriaDTO> categoriasDTO = categorias
			.stream()
			.map(categoria -> new CategoriaDTO(categoria))
			.collect(Collectors.toList());
		
		return categoriasDTO;
	}

	@Transactional(readOnly = true)
	public CategoriaDTO buscarPorId(Long id) {
		Optional<Categoria> opcional = repository.findById(id);
		
		Categoria categoria = opcional.orElseThrow(
			() -> new EntidadeNaoEncontradaException("Entidade não encontrada")
		);
		
		CategoriaDTO dto = new CategoriaDTO(categoria);
		return dto;
	}
}
