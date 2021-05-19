package br.pro.delfino.dscatalog.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_categorias")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long id;
	
	@Column(length = 50)
	@Getter
	@Setter
	private String nome;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	@Getter
	private Instant criadoEm;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	@Getter
	private Instant atualizadoEm;
	
	@PrePersist
	public void antesInserir() {
		criadoEm = Instant.now();
	}
	
	@PreUpdate
	public void antesAtualizar() {
		atualizadoEm = Instant.now();
	}
}
