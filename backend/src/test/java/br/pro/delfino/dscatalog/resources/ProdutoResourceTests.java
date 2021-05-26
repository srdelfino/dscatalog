package br.pro.delfino.dscatalog.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

@WebMvcTest(ProdutoResource.class)
public class ProdutoResourceTests {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProdutoService servico;
	
	private ProdutoDTO dto;
	
	private Page<ProdutoDTO> pagina;
	
	@BeforeEach
	public void configurar() {
		dto = ProdutoDTOFactory.criar();
		
		pagina = new PageImpl<>(List.of(dto));
		
		when(servico.buscarTudo(any())).thenReturn(pagina);
	}
	
	@Test
	public void buscarTudoDeveriaRetornarPagina() throws Exception {
		ResultActions resultado = mockMvc
									.perform(get("/produtos")
										.accept(MediaType.APPLICATION_JSON));
		
		resultado.andExpect(status().isOk());
	}
}
