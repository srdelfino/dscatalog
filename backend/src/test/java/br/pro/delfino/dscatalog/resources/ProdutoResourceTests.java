package br.pro.delfino.dscatalog.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import br.pro.delfino.dscatalog.dto.ProdutoDTO;
import br.pro.delfino.dscatalog.factories.ProdutoDTOFactory;
import br.pro.delfino.dscatalog.services.ProdutoService;
import br.pro.delfino.dscatalog.services.exceptions.EntidadeNaoEncontradaException;

@WebMvcTest(ProdutoResource.class)
public class ProdutoResourceTests {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProdutoService servico;
	
	private ProdutoDTO dto;
	
	private Page<ProdutoDTO> pagina;
	
	private Long idExistente;
	private Long idNaoExistente;
	
	@BeforeEach
	public void configurar() {
		dto = ProdutoDTOFactory.criar();
		
		pagina = new PageImpl<>(List.of(dto));
		
		idExistente = 1L;
		idNaoExistente = 2L;
		
		when(servico.buscarTudo(any())).thenReturn(pagina);
		
		when(servico.buscarPorId(idExistente)).thenReturn(dto);
		when(servico.buscarPorId(idNaoExistente)).thenThrow(EntidadeNaoEncontradaException.class);
	}
	
	@Test
	public void buscarTudoDeveriaRetornarPagina() throws Exception {
		ResultActions resultado = mockMvc
									.perform(get("/produtos")
										.accept(MediaType.APPLICATION_JSON));
		
		resultado.andExpect(status().isOk());
	}
	
	@Test
	public void buscarPorCodigoDeveriaRetornarProdutoQuandoIdExistir() throws Exception {
		ResultActions resultado = 
			mockMvc.perform(
				get("/produtos/{id}", idExistente).accept(MediaType.APPLICATION_JSON));
	
		resultado.andExpect(status().isOk());
		resultado.andExpect(jsonPath("$.id").exists());
		resultado.andExpect(jsonPath("$.nome").exists());
		resultado.andExpect(jsonPath("$.descricao").exists());
	}
	
	@Test
	public void buscarPorCodigoDeveriaRetornarNaoEncontradoQuandoIdNaoExistir() throws Exception {
		ResultActions resultado = 
				mockMvc.perform(
					get("/produtos/{id}", idNaoExistente).accept(MediaType.APPLICATION_JSON));
		
		resultado.andExpect(status().isNotFound());
	}
}
