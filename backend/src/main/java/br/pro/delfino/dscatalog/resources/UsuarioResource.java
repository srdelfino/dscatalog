package br.pro.delfino.dscatalog.resources;

import java.net.URI;

import javax.validation.Valid;

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

import br.pro.delfino.dscatalog.dto.UsuarioDTO;
import br.pro.delfino.dscatalog.dto.UsuarioInsercaoDTO;
import br.pro.delfino.dscatalog.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {
	@Autowired
	private UsuarioService servico;

	@GetMapping
	public ResponseEntity<Page<UsuarioDTO>> buscarTudo(Pageable paginacao) {
		Page<UsuarioDTO> lista = servico.buscarTudo(paginacao);
		return ResponseEntity.ok(lista);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
		UsuarioDTO dto = servico.buscarPorId(id);
		return ResponseEntity.ok(dto);
	}

	@PostMapping
	public ResponseEntity<UsuarioDTO> inserir(@Valid @RequestBody UsuarioInsercaoDTO dto) {
		UsuarioDTO dtoRetorno = servico.inserir(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoRetorno.getId()).toUri();
		return ResponseEntity.created(uri).body(dtoRetorno);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> editar(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto) {
		dto = servico.editar(id, dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		servico.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
