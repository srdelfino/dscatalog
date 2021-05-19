package br.pro.delfino.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.pro.delfino.dscatalog.dto.CategoriaDTO;
import br.pro.delfino.dscatalog.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	@Autowired
	private CategoriaService service;
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> buscarTudo(){
		List<CategoriaDTO> lista = service.buscarTudo();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id){
		CategoriaDTO dto = service.buscarPorId(id);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<CategoriaDTO> inserir(@RequestBody CategoriaDTO dto){
		dto = service.inserir(dto);
		
		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest().path("/{id}")
			.buildAndExpand(dto.getId())
			.toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
}
