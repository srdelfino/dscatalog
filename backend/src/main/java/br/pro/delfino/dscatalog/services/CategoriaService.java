package br.pro.delfino.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pro.delfino.dscatalog.dto.CategoriaDTO;
import br.pro.delfino.dscatalog.entities.Categoria;
import br.pro.delfino.dscatalog.repositories.CategoriaRepository;
import br.pro.delfino.dscatalog.services.exceptions.EntidadeNaoEncontradaException;
import br.pro.delfino.dscatalog.services.exceptions.ViolacaoIntegridadeDadosException;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository repositorio;

	@Transactional(readOnly = true)
	public List<CategoriaDTO> buscarTudo() {
		List<Categoria> lista = repositorio.findAll();

		List<CategoriaDTO> listaDTO = lista.stream().map(entidade -> new CategoriaDTO(entidade))
				.collect(Collectors.toList());

		return listaDTO;
	}
	
	@Transactional(readOnly = true)
	public Page<CategoriaDTO> buscarTudo(Pageable paginacao) {
		Page<Categoria> lista = repositorio.findAll(paginacao);
		
		Page<CategoriaDTO> listaDTO = lista
			.map(entidade -> new CategoriaDTO(entidade));
			
		return listaDTO;
	}

	@Transactional(readOnly = true)
	public CategoriaDTO buscarPorId(Long id) {
		Optional<Categoria> opcional = repositorio.findById(id);

		Categoria entidade = opcional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));

		CategoriaDTO dto = new CategoriaDTO(entidade);
		return dto;
	}

	@Transactional
	public CategoriaDTO inserir(CategoriaDTO dto) {
		Categoria entidade = new Categoria();
		entidade.setNome(dto.getNome());

		repositorio.save(entidade);

		dto = new CategoriaDTO(entidade);
		return dto;
	}

	@Transactional
	public CategoriaDTO editar(Long id, CategoriaDTO dto) {
		try {
			Categoria entidade = repositorio.getOne(id);
			entidade.setNome(dto.getNome());

			repositorio.save(entidade);

			dto = new CategoriaDTO(entidade);
			return dto;
		} catch (EntityNotFoundException excecao) {
			throw new EntidadeNaoEncontradaException("ID não encontrado: " + id);
		}
	}

	@Transactional
	public void excluir(Long id) {
		try {
			repositorio.deleteById(id);
			repositorio.flush();
		} catch (EmptyResultDataAccessException excecao) {
			throw new EntidadeNaoEncontradaException("ID não encontrado: " + id);
		} catch (DataIntegrityViolationException excecao) {
			throw new ViolacaoIntegridadeDadosException("Violação da integridade referencial");
		}
	}
}
