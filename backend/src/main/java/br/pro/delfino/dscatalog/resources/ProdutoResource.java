package br.pro.delfino.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.pro.delfino.dscatalog.dto.ProdutoDTO;
import br.pro.delfino.dscatalog.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	@Autowired
	private ProdutoService servico;

	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> buscarTudo(Pageable paginacao) {
		Page<ProdutoDTO> lista = servico.buscarTudo(paginacao);
		return ResponseEntity.ok(lista);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
		ProdutoDTO dto = servico.buscarPorId(id);
		return ResponseEntity.ok(dto);
	}

	@PostMapping
	public ResponseEntity<ProdutoDTO> inserir(@RequestBody ProdutoDTO dto) {
		dto = servico.inserir(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ProdutoDTO> editar(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
		dto = servico.editar(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		servico.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
