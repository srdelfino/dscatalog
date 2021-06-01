package br.pro.delfino.dscatalog.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.pro.delfino.dscatalog.entities.Categoria;
import br.pro.delfino.dscatalog.entities.Produto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	@Size(min = 5, max = 60, message = "Nome deve ter entre 5 e 60 caracteres")
	@Getter
	@Setter
	private String nome;
	
	@NotBlank(message = "Campo obrigatório")
	@Getter
	@Setter
	private String descricao;
	
	@Positive(message = "Preço deve ser um valor positivo")
	@Getter
	@Setter
	private BigDecimal preco;
	
	@Getter
	@Setter
	private String imagem;
	
	@PastOrPresent(message = "A data do produto não pode ser futura")
	@Getter
	@Setter
	private Instant data;
	
	@Getter
	private List<CategoriaDTO> categorias = new ArrayList<>();

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
