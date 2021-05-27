package br.pro.delfino.dscatalog.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import br.pro.delfino.dscatalog.dto.ProdutoDTO;
import br.pro.delfino.dscatalog.repositories.ProdutoRepository;
import br.pro.delfino.dscatalog.services.exceptions.EntidadeNaoEncontradaException;

@SpringBootTest
@Transactional
public class ProdutoServiceTestsIT {
	@Autowired
	private ProdutoRepository repositorio;
	
	@Autowired
	private ProdutoService servico;

	private Long idExistente;
	private Long idNaoExistente;
	private Long totalDeProdutos;

	@BeforeEach
	public void configurar() {
		idExistente = 1L;
		idNaoExistente = 1000L;
		totalDeProdutos = 25L;
	}
	
	@Test
	public void buscarTudoDeveriaRetornarPaginaQuandoPagina0Tamanho10() {
		PageRequest paginacao = PageRequest.of(0, 10);
		
		Page<ProdutoDTO> pagina = servico.buscarTudo(paginacao);
		
		assertFalse(pagina.isEmpty());
		assertEquals(0, pagina.getNumber());
		assertEquals(10, pagina.getSize());
		assertEquals(totalDeProdutos, pagina.getTotalElements());
	}
	
	@Test
	public void buscarTudoDeveriaRetornarPaginaVaziaQuandoPaginaNaoExiste() {
		PageRequest paginacao = PageRequest.of(4, 10);
		
		Page<ProdutoDTO> pagina = servico.buscarTudo(paginacao);
		
		assertTrue(pagina.isEmpty());
	}
	
	@Test
	public void buscarTudoDeveriaRetornarPaginaOrdenadaQuandoOrdenadoPorNome() {
		PageRequest paginacao = PageRequest.of(0, 10, Sort.by("nome"));
		
		Page<ProdutoDTO> pagina = servico.buscarTudo(paginacao);
		
		assertFalse(pagina.isEmpty());
		assertEquals("Macbook Pro", pagina.getContent().get(0).getNome());
		assertEquals("PC Gamer", pagina.getContent().get(1).getNome());
		assertEquals("PC Gamer Alfa", pagina.getContent().get(2).getNome());
	}
	
	@Test
	public void excluirDeveriaRemoverRecursoQuandoIdExistir() {
		servico.excluir(idExistente);
		
		assertEquals(totalDeProdutos - 1, repositorio.count());
	}
	
	@Test
	public void excluirDeveriaLancarEntidadeNaoEncontradaExceptionQuandoIdNaoExistir() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> {
			servico.excluir(idNaoExistente);
		});
	}
}
