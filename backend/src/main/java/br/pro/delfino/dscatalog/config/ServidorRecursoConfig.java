package br.pro.delfino.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ServidorRecursoConfig extends ResourceServerConfigurerAdapter {
	@Autowired
	private JwtTokenStore armazenadorDoToken;
	
	private static final String[] PUBLICO = {"/oauth/token"};
	private static final String[] OPERADOR_OU_ADMINISTRADOR = {"/categorias/**", "/produtos/**"};
	private static final String[] ADMINISTRADOR = {"/usuarios/**"};
	
	@Override
	public void configure(ResourceServerSecurityConfigurer recursos) throws Exception {
		recursos.tokenStore(armazenadorDoToken);
	} 
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(PUBLICO).permitAll()
				.antMatchers(HttpMethod.GET, OPERADOR_OU_ADMINISTRADOR).permitAll()
				.antMatchers(OPERADOR_OU_ADMINISTRADOR).hasAnyRole("OPERADOR", "ADMINISTRADOR")
				.antMatchers(ADMINISTRADOR).hasAnyRole("ADMINISTRADOR")
				.anyRequest().authenticated();
	}
	

}
