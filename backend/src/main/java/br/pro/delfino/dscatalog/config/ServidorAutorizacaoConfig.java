package br.pro.delfino.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class ServidorAutorizacaoConfig extends AuthorizationServerConfigurerAdapter {
	@Value("${security.oauth2.client.client-id}")
	private String cliente;
	
	@Value("${security.oauth2.client.client-secret}")
	private String segredo;
	
	@Value("${jwt.duration}")
	private Integer duracao;
	
	@Autowired
	private BCryptPasswordEncoder codificador;
	
	@Autowired
	private JwtAccessTokenConverter conversorDoTokenDeAcesso;
	
	@Autowired
	private JwtTokenStore armazenadorDoToken;
	
	@Autowired
	private AuthenticationManager gerenciadorDeAutenticacao;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer seguranca) throws Exception {
		seguranca
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clientes) throws Exception {
		clientes
			.inMemory()
			.withClient(cliente)
			.secret(codificador.encode(segredo))
			.scopes("read", "write")
			.authorizedGrantTypes("password")
			.accessTokenValiditySeconds(duracao);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer pontosFinais) throws Exception {
		pontosFinais
			.authenticationManager(gerenciadorDeAutenticacao)
			.tokenStore(armazenadorDoToken)
			.accessTokenConverter(conversorDoTokenDeAcesso);
	}
}
