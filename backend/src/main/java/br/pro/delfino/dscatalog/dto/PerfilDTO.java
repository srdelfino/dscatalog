package br.pro.delfino.dscatalog.dto;

import java.io.Serializable;

import br.pro.delfino.dscatalog.entities.Perfil;
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
public class PerfilDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String nome;
	
	public PerfilDTO (Perfil perfil) {
		this.id = perfil.getId();
		this.nome = perfil.getNome();
	}
}
