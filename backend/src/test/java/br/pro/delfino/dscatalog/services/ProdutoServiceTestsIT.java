package br.pro.delfino.dscatalog.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.pro.delfino.dscatalog.repositories.ProdutoRepository;
import br.pro.delfino.dscatalog.services.exceptions.EntidadeNaoEncontradaException;

@SpringBootTest
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
