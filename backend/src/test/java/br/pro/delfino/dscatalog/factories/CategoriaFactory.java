package br.pro.delfino.dscatalog.factories;

import br.pro.delfino.dscatalog.entities.Categoria;

public class CategoriaFactory {
	public static Categoria criar() {
		Categoria categoria = new Categoria(
			1L,
			"Books"
		);
		
		return categoria;
	}
}
