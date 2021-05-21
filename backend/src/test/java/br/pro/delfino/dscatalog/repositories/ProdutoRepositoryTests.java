package br.pro.delfino.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import br.pro.delfino.dscatalog.entities.Produto;

@DataJpaTest
public class ProdutoRepositoryTests {
	@Autowired
	private ProdutoRepository repositorio;
	
	@Test
	public void excluirDeveriaRemoverObjetoQuandoIDExistir() {
		Long id = 1L;
		
		repositorio.deleteById(id);
		Optional<Produto> opcional = repositorio.findById(id);
		
		Assertions.assertFalse(opcional.isPresent());
	}
	
	@Test
	public void excluirDeveriaLancarEmptyResultDataAccessExceptionQuandoIdNaoExiste() {
		Long id = 1000L;
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {	
			repositorio.deleteById(id);
		});
	}	
}
