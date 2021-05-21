package br.pro.delfino.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import br.pro.delfino.dscatalog.entities.Produto;

@DataJpaTest
public class ProdutoRepositoryTests {
	@Autowired
	private ProdutoRepository repositorio;
	
	private Long idExistente;
	private Long idNaoExistente;
	
	@BeforeEach
	public void configurar() {
		idExistente = 1L;
		idNaoExistente = 1000L;
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
}
