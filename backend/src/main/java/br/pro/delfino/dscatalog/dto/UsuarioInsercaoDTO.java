package br.pro.delfino.dscatalog.dto;

import br.pro.delfino.dscatalog.entities.Usuario;
import br.pro.delfino.dscatalog.services.validations.UsuarioInsercaoValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@UsuarioInsercaoValid
public class UsuarioInsercaoDTO extends UsuarioDTO {
	private static final long serialVersionUID = 1L;

	private String senha;
	
	public UsuarioInsercaoDTO(Usuario usuario) {
		super(usuario);
	}
}
