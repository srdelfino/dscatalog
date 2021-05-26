package br.pro.delfino.dscatalog.factory;

import br.pro.delfino.dscatalog.dto.ProdutoDTO;
import br.pro.delfino.dscatalog.entities.Produto;

public class ProdutoDTOFactory {
	public static ProdutoDTO criar() {
		Produto produto = ProdutoFactory.criar();
		ProdutoDTO dto = new ProdutoDTO(produto, produto.getCategorias());
		return dto;
	}
}
