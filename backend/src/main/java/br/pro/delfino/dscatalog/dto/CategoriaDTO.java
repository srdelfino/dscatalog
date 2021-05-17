package br.pro.delfino.dscatalog.dto;

import java.io.Serializable;

import br.pro.delfino.dscatalog.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	
	public CategoriaDTO (Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}
}
