package br.pro.delfino.dscatalog.factories;

import java.math.BigDecimal;
import java.time.Instant;

import br.pro.delfino.dscatalog.entities.Categoria;
import br.pro.delfino.dscatalog.entities.Produto;

public class ProdutoFactory {
	public static Produto criar() {
		Produto produto = new Produto(
			1L,
			"Phone",
			"Good Phone",
			BigDecimal.valueOf(800.0),
			"https://img.com/img.png",
			Instant.parse("2020-10-20T03:00:00Z"));
		
		produto
			.getCategorias()
			.add(
				new Categoria(2L, null));
		return produto;
	}
}
