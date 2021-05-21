package br.pro.delfino.dscatalog.dto;

import java.io.Serializable;

import br.pro.delfino.dscatalog.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String nome;
	
	public CategoriaDTO (Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}
}
