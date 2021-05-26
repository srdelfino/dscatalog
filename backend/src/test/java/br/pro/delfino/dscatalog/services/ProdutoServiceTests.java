package br.pro.delfino.dscatalog.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

		doNothing().when(repositorio).deleteById(idExistente);
		doThrow(EmptyResultDataAccessException.class).when(repositorio).deleteById(idNaoExistente);
	}

	@Test
	public void excluirDeveriaFazerNadaQuandoIdExistir() {
		assertDoesNotThrow(() -> {
			servico.excluir(idExistente);
		});

		verify(repositorio, times(1)).deleteById(idExistente);
	}
}
