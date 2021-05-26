package br.pro.delfino.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.pro.delfino.dscatalog.repositories.ProdutoRepository;

@ExtendWith(SpringExtension.class)
public class ProdutoServiceTests {
	@InjectMocks
	private ProdutoService servico;
	
	@Mock
	private ProdutoRepository repositorio;
	
	private Long idExistente;
	private Long idNaoExistente;
	
	@BeforeEach
	public void configurar() {
		idExistente = 1L;
		idNaoExistente = 1000L;
		
		Mockito.doNothing().when(repositorio).deleteById(idExistente);;
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repositorio).deleteById(idNaoExistente);
	}
	
	@Test
	public void excluirDeveriaFazerNadaQuandoIdExistir() {
		Assertions.assertDoesNotThrow(() -> {
			servico.excluir(idExistente);
		});
		
		Mockito.verify(
			repositorio, Mockito.times(1)).deleteById(idExistente);
	}
}
