package br.pro.delfino.dscatalog.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.pro.delfino.dscatalog.dto.UsuarioDTO;
import br.pro.delfino.dscatalog.dto.UsuarioEdicaoDTO;
import br.pro.delfino.dscatalog.dto.UsuarioInsercaoDTO;
import br.pro.delfino.dscatalog.entities.Usuario;
import br.pro.delfino.dscatalog.repositories.UsuarioRepository;
import br.pro.delfino.dscatalog.resources.exceptions.MensagemDeCampo;


public class UsuarioEdicaoValidator implements ConstraintValidator<UsuarioEdicaoValid, UsuarioEdicaoDTO> {
	@Autowired
	private UsuarioRepository repositorio;
	
	@Override
	public void initialize(UsuarioEdicaoValid anotacao) {
	}

	@Override
	public boolean isValid(UsuarioEdicaoDTO dto, ConstraintValidatorContext contexto) {
		
		List<MensagemDeCampo> erros = new ArrayList<>();
		
		Usuario entidade = repositorio.findByEmail(dto.getEmail());
		
		if(entidade != null ) {
			erros.add(new MensagemDeCampo("email", "Email já existe"));
		}
		
		erros.forEach(erro -> {
			contexto.disableDefaultConstraintViolation();
			contexto.buildConstraintViolationWithTemplate(erro.getMensagem()).addPropertyNode(erro.getNomeDoCampo())
					.addConstraintViolation();
		});
		
		return erros.isEmpty();
	}
}