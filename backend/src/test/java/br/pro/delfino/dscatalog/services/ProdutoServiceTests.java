package br.pro.delfino.dscatalog.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.pro.delfino.dscatalog.repositories.ProdutoRepository;
import br.pro.delfino.dscatalog.services.exceptions.EntidadeNaoEncontradaException;
import br.pro.delfino.dscatalog.services.exceptions.ViolacaoIntegridadeDadosException;

@ExtendWith(SpringExtension.class)
public class ProdutoServiceTests {
	@InjectMocks
	private ProdutoService servico;

	@Mock
	private ProdutoRepository repositorio;

	private Long idExistente;
	private Long idNaoExistente;
	private Long idDependente;

	@BeforeEach
	public void configurar() {
		idExistente = 1L;
		idNaoExistente = 1000L;
		idDependente = 4L;

		doNothing().when(repositorio).deleteById(idExistente);
		
		doThrow(EmptyResultDataAccessException.class).when(repositorio).deleteById(idNaoExistente);
		
		doThrow(DataIntegrityViolationException.class).when(repositorio).deleteById(idDependente);
	} 

	@Test
	public void excluirDeveriaFazerNadaQuandoIdExistir() {
		assertDoesNotThrow(() -> {
			servico.excluir(idExistente);
		});

		verify(repositorio, times(1)).deleteById(idExistente);
	}
	
	@Test
	public void excluirDeveriaLancarEntidadeNaoEncontradaExceptionQuandoIdDependente() {
		assertThrows(ViolacaoIntegridadeDadosException.class, () -> {
			servico.excluir(idDependente);
		});

		verify(repositorio, times(1)).deleteById(idDependente);
	}
	
	@Test
	public void excluirDeveriaLancarViolacaoIntegridadeDadosExceptionQuandoIdNaoExistir() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> {
			servico.excluir(idNaoExistente);
		});

		verify(repositorio, times(1)).deleteById(idNaoExistente);
	}
}
