package br.pro.delfino.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import br.pro.delfino.dscatalog.entities.Produto;
import br.pro.delfino.dscatalog.factories.ProdutoFactory;

@DataJpaTest
public class ProdutoRepositoryTests {
	@Autowired
	private ProdutoRepository repositorio;
	
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
	public void excluirDeveriaRemoverObjetoQuandoIDExistir() {
		repositorio.deleteById(idExistente);
		Optional<Produto> opcional = repositorio.findById(idExistente);
		
		Assertions.assertFalse(opcional.isPresent());
	}
	
	@Test
	public void excluirDeveriaLancarEmptyResultDataAccessExceptionQuandoIdNaoExiste() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {	
			repositorio.deleteById(idNaoExistente);
		});
	}	
	
	@Test
	public void inserirDeveriarPersistirComAutoIncrementoQuandoIdEhNulo() {
		Produto produto = ProdutoFactory.criar();
		produto.setId(null);
		
		repositorio.save(produto);
		
		Assertions.assertNotNull(produto.getId());
		Assertions.assertEquals(totalDeProdutos + 1, produto.getId());
	}
	
	@Test
	public void buscarPorCodigoDeveriaRetornarNaoVazioQuandoIdExistir(){
		Optional<Produto> opcional = repositorio.findById(idExistente);
		Assertions.assertTrue(opcional.isPresent());
	}
	
	@Test
	public void buscarPorCodigoDeveriaRetornarVazioQuandoIdNaoExistir(){
		Optional<Produto> opcional = repositorio.findById(idNaoExistente);
		Assertions.assertTrue(opcional.isEmpty());
	}
}
