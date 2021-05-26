package br.pro.delfino.dscatalog.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.pro.delfino.dscatalog.dto.ProdutoDTO;
import br.pro.delfino.dscatalog.entities.Categoria;
import br.pro.delfino.dscatalog.entities.Produto;
import br.pro.delfino.dscatalog.factories.CategoriaFactory;
import br.pro.delfino.dscatalog.factories.ProdutoDTOFactory;
import br.pro.delfino.dscatalog.factories.ProdutoFactory;
import br.pro.delfino.dscatalog.repositories.CategoriaRepository;
import br.pro.delfino.dscatalog.repositories.ProdutoRepository;
import br.pro.delfino.dscatalog.services.exceptions.EntidadeNaoEncontradaException;
import br.pro.delfino.dscatalog.services.exceptions.ViolacaoIntegridadeDadosException;

@ExtendWith(SpringExtension.class)
public class ProdutoServiceTests {
	@InjectMocks
	private ProdutoService servico;

	@Mock
	private CategoriaRepository categoriaRepositorio;
	
	@Mock
	private ProdutoRepository produtoRepositorio;

	private Long idExistente;
	private Long idNaoExistente;
	private Long idDependente;
	
	private Categoria categoria;
	private Produto produto;
	
	private Page<Produto> pagina;
	
	private ProdutoDTO dto;

	@BeforeEach
	public void configurar() {
		idExistente = 1L;
		idNaoExistente = 2L;
		idDependente = 3L;
		
		categoria = CategoriaFactory.criar();
		produto = ProdutoFactory.criar();
		
		pagina = new PageImpl<>(List.of(produto));
		
		dto = ProdutoDTOFactory.criar();
		
		when(produtoRepositorio.save(ArgumentMatchers.any())).thenReturn(produto);
		
		when(produtoRepositorio.findAll((Pageable) ArgumentMatchers.any())).thenReturn(pagina);
		
		when(produtoRepositorio.findById(idExistente)).thenReturn(Optional.of(produto));
		when(produtoRepositorio.findById(idNaoExistente)).thenReturn(Optional.empty());

		doNothing().when(produtoRepositorio).deleteById(idExistente);
		doThrow(EmptyResultDataAccessException.class).when(produtoRepositorio).deleteById(idNaoExistente);
		doThrow(DataIntegrityViolationException.class).when(produtoRepositorio).deleteById(idDependente);
		
		when(produtoRepositorio.getOne(idExistente)).thenReturn(produto);
		when(produtoRepositorio.getOne(idNaoExistente)).thenThrow(EntityNotFoundException.class);
		when(categoriaRepositorio.getOne(idExistente)).thenReturn(categoria);
		when(categoriaRepositorio.getOne(idNaoExistente)).thenThrow(EntityNotFoundException.class);
	} 
	
	@Test
	public void buscarTudoDeveriaRetornarPagina() {
		Pageable paginacao = PageRequest.of(0, 10);
		Page<ProdutoDTO> pagina = servico.buscarTudo(paginacao);
		
		assertNotNull(pagina);
		verify(produtoRepositorio, times(1)).findAll(paginacao);
	}
	
	@Test
	public void buscarPorIdDeveriaRetornarProdutoDTOQuandoIdExistir() {
		ProdutoDTO dto = servico.buscarPorId(idExistente);
		
		assertNotNull(dto);
		verify(produtoRepositorio, times(1)).findById(idExistente);
	}
	
	@Test
	public void buscarPorIdDeveriaLancarEntidadeNaoEncontradaExceptionQuandoIdNaoExistir() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> {
			servico.buscarPorId(idNaoExistente);
		});
		
		verify(produtoRepositorio, times(1)).findById(idNaoExistente);
	}

	@Test
	public void excluirDeveriaFazerNadaQuandoIdExistir() {
		assertDoesNotThrow(() -> {
			servico.excluir(idExistente);
		});

		verify(produtoRepositorio, times(1)).deleteById(idExistente);
	}
	
	@Test
	public void excluirDeveriaLancarEntidadeNaoEncontradaExceptionQuandoIdDependente() {
		assertThrows(ViolacaoIntegridadeDadosException.class, () -> {
			servico.excluir(idDependente);
		});

		verify(produtoRepositorio, times(1)).deleteById(idDependente);
	}
	
	@Test
	public void excluirDeveriaLancarViolacaoIntegridadeDadosExceptionQuandoIdNaoExistir() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> {
			servico.excluir(idNaoExistente);
		});

		verify(produtoRepositorio, times(1)).deleteById(idNaoExistente);
	}
	
	@Test
	public void editarDeveriaRetornarProdutoDTOQuandoIdExistir() {
		ProdutoDTO dto = servico.editar(idExistente, this.dto);
		
		assertNotNull(dto);
		verify(produtoRepositorio, times(1)).save(produto);
	}
	
	@Test
	public void editarDeveriaLancarEntidadeNaoEncontradaExceptionQuandoIdNaoExistir() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> {
			servico.editar(idNaoExistente, this.dto);
		});
		
		verify(produtoRepositorio, times(0)).save(produto);
	}
}
