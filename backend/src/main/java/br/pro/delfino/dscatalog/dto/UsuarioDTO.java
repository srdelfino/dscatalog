package br.pro.delfino.dscatalog.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.pro.delfino.dscatalog.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	private String nome;
	
	@Getter
	@Setter
	private String sobrenome;
	
	@Getter
	@Setter
	private String email;
	
	@Getter
	private Set<PerfilDTO> perfis = new HashSet<>();
	
	public UsuarioDTO (Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.sobrenome = usuario.getSobrenome();
		
		usuario.getPerfis().forEach(perfil -> this.perfis.add(new PerfilDTO(perfil)));
	}
}
