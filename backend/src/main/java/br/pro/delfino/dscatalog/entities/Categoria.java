package br.pro.delfino.dscatalog.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "categoria")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	private String nome;
	
	@ManyToMany(mappedBy = "categorias")
	@Getter
	private Set<Produto> produtos = new HashSet<>();
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	@Getter
	private Instant criadoEm;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	@Getter
	private Instant atualizadoEm;
	
	public Categoria(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	@PrePersist
	public void antesInserir() {
		criadoEm = Instant.now();
	}
	
	@PreUpdate
	public void antesAtualizar() {
		atualizadoEm = Instant.now();
	}
}
