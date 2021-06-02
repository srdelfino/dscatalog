package br.pro.delfino.dscatalog.services.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.pro.delfino.dscatalog.dto.UsuarioEdicaoDTO;
import br.pro.delfino.dscatalog.entities.Usuario;
import br.pro.delfino.dscatalog.repositories.UsuarioRepository;
import br.pro.delfino.dscatalog.resources.exceptions.MensagemDeCampo;


public class UsuarioEdicaoValidator implements ConstraintValidator<UsuarioEdicaoValid, UsuarioEdicaoDTO> {
	@Autowired
	private UsuarioRepository repositorio;
	
	@Autowired
	private HttpServletRequest requisicao;
	
	@Override
	public void initialize(UsuarioEdicaoValid anotacao) {
	}

	@Override
	public boolean isValid(UsuarioEdicaoDTO dto, ConstraintValidatorContext contexto) {
		@SuppressWarnings("unchecked")
		Map<String, String> variaveis = (Map<String, String>) requisicao.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long id = Long.valueOf(variaveis.get("id"));
		
		List<MensagemDeCampo> erros = new ArrayList<>();
		
		Usuario entidade = repositorio.findByEmail(dto.getEmail());
		
		if(entidade != null && id != entidade.getId()) {
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