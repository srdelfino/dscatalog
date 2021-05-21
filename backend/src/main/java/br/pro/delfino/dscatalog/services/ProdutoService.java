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

import br.pro.delfino.dscatalog.dto.ProdutoDTO;
import br.pro.delfino.dscatalog.entities.Categoria;
import br.pro.delfino.dscatalog.entities.Produto;
import br.pro.delfino.dscatalog.repositories.CategoriaRepository;
import br.pro.delfino.dscatalog.repositories.ProdutoRepository;
import br.pro.delfino.dscatalog.services.exceptions.EntidadeNaoEncontradaException;
import br.pro.delfino.dscatalog.services.exceptions.ViolacaoIntegridadeDadosException;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepositorio;
	
	@Autowired
	private CategoriaRepository categoriaRepositorio;

	@Transactional(readOnly = true)
	public List<ProdutoDTO> buscarTudo() {
		List<Produto> lista = produtoRepositorio.findAll();

		List<ProdutoDTO> listaDTO = lista.stream().map(entidade -> new ProdutoDTO(entidade))
				.collect(Collectors.toList());

		return listaDTO;
	}
	
	@Transactional(readOnly = true)
	public Page<ProdutoDTO> buscarTudo(Pageable paginacao) {
		Page<Produto> lista = produtoRepositorio.findAll(paginacao);
		
		Page<ProdutoDTO> listaDTO = lista
			.map(entidade -> new ProdutoDTO(entidade));
			
		return listaDTO;
	}

	@Transactional(readOnly = true)
	public ProdutoDTO buscarPorId(Long id) {
		Optional<Produto> opcional = produtoRepositorio.findById(id);

		Produto entidade = opcional.orElseThrow(() -> new EntidadeNaoEncontradaException("Entidade não encontrada"));

		ProdutoDTO dto = new ProdutoDTO(entidade, entidade.getCategorias());
		return dto;
	}

	@Transactional
	public ProdutoDTO inserir(ProdutoDTO dto) {
		Produto entidade = new Produto();
		converterDTOParaEntidade(dto, entidade);

		produtoRepositorio.save(entidade);

		dto = new ProdutoDTO(entidade);
		return dto;
	}

	@Transactional
	public ProdutoDTO editar(Long id, ProdutoDTO dto) {
		try {
			Produto entidade = produtoRepositorio.getOne(id);
			converterDTOParaEntidade(dto, entidade);

			produtoRepositorio.save(entidade);

			dto = new ProdutoDTO(entidade);
			return dto;
		} catch (EntityNotFoundException excecao) {
			throw new EntidadeNaoEncontradaException("ID não encontrado: " + id);
		}
	}

	@Transactional
	public void excluir(Long id) {
		try {
			produtoRepositorio.deleteById(id);
			produtoRepositorio.flush();
		} catch (EmptyResultDataAccessException excecao) {
			throw new EntidadeNaoEncontradaException("ID não encontrado: " + id);
		} catch (DataIntegrityViolationException excecao) {
			throw new ViolacaoIntegridadeDadosException("Violação da integridade referencial");
		}
	}
	
	private void converterDTOParaEntidade(ProdutoDTO dto, Produto entidade) {
		entidade.setData(dto.getData());
		entidade.setDescricao(dto.getDescricao());
		entidade.setImagem(dto.getImagem());
		entidade.setNome(dto.getNome());
		entidade.setPreco(dto.getPreco());
		
		entidade.getCategorias().clear();
		
		dto.getCategorias().forEach(categoriaDTO -> {
			Categoria categoriaEntidade = categoriaRepositorio.getOne(categoriaDTO.getId());
			entidade.getCategorias().add(categoriaEntidade);
		});
	}
}
