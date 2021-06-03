package br.pro.delfino.dscatalog.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import br.pro.delfino.dscatalog.entities.Usuario;
import br.pro.delfino.dscatalog.repositories.UsuarioRepository;

@Component
public class MelhoradorToken implements TokenEnhancer {
	@Autowired
	private UsuarioRepository repositorio;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken tokenDeAcesso, OAuth2Authentication autenticacao) {
		Usuario usuario = repositorio.findByEmail(autenticacao.getName());
		
		Map<String, Object> informacoesAdicionais = new HashMap<>();
		informacoesAdicionais.put("usuarioId", usuario.getId());
		informacoesAdicionais.put("usuarioNome", usuario.getNome());
		
		DefaultOAuth2AccessToken tokenDeAcessoPadrao = (DefaultOAuth2AccessToken) tokenDeAcesso;
		tokenDeAcessoPadrao.setAdditionalInformation(informacoesAdicionais);
		
		return tokenDeAcessoPadrao;
	}

}
