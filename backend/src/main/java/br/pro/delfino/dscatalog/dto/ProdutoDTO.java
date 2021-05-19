package br.pro.delfino.dscatalog.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import br.pro.delfino.dscatalog.entities.Categoria;
import br.pro.delfino.dscatalog.entities.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	private String nome;
	
	@Getter
	@Setter
	private String descricao;
	
	@Getter
	@Setter
	private BigDecimal preco;
	
	@Getter
	@Setter
	private String imagem;
	
	@Getter
	@Setter
	private Instant data;
	
	@Getter
	private List<CategoriaDTO> categorias;

	public ProdutoDTO(Long id, String nome, String descricao, BigDecimal preco, String imagem, Instant data) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.imagem = imagem;
		this.data = data;
	}
	
	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.preco = produto.getPreco();
		this.imagem = produto.getImagem();
		this.data = produto.getData();
	}
	
	public ProdutoDTO(Produto produto, Set<Categoria> categorias) {
		this (produto);
		categorias.forEach(categoria -> this.categorias.add(new CategoriaDTO(categoria)));
	}
}
