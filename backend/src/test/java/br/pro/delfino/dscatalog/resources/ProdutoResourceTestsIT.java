package br.pro.delfino.dscatalog.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProdutoResourceTestsIT {
	@Autowired
	private MockMvc mockMvc;

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
	public void buscarTudoDeveriaRetornarPaginaOrdenadaQuandoOrdenadoPorNome() throws Exception {
		ResultActions resultado = 
			mockMvc
				.perform(get("/produtos")
				.queryParam("page", "0")
				.queryParam("size", "10")
				.queryParam("sort", "nome,asc")
				.accept(MediaType.APPLICATION_JSON));
		
		resultado.andExpect(status().isOk());
		resultado.andExpect(jsonPath("$.totalElements").value(totalDeProdutos));
		resultado.andExpect(jsonPath("$.content").exists());
		resultado.andExpect(jsonPath("$.content[0].nome").value("Macbook Pro"));
		resultado.andExpect(jsonPath("$.content[1].nome").value("PC Gamer"));
		resultado.andExpect(jsonPath("$.content[2].nome").value("PC Gamer Alfa"));
	}
}
