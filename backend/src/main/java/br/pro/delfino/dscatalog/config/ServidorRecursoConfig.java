package br.pro.delfino.dscatalog.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
	private Environment ambiente;
	
	@Autowired
	private JwtTokenStore armazenadorDoToken;
	
	private static final String[] PUBLICO = {"/oauth/token", "/h2-console/**"};
	private static final String[] OPERADOR_OU_ADMINISTRADOR = {"/categorias/**", "/produtos/**"};
	private static final String[] ADMINISTRADOR = {"/usuarios/**"};
	
	@Override
	public void configure(ResourceServerSecurityConfigurer recursos) throws Exception {
		recursos.tokenStore(armazenadorDoToken);
	} 
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		List<String> perfis = Arrays.asList(ambiente.getActiveProfiles());
		
		// H2
		if(perfis.contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http
			.authorizeRequests()
				.antMatchers(PUBLICO).permitAll()
				.antMatchers(HttpMethod.GET, OPERADOR_OU_ADMINISTRADOR).permitAll()
				.antMatchers(OPERADOR_OU_ADMINISTRADOR).hasAnyRole("OPERADOR", "ADMINISTRADOR")
				.antMatchers(ADMINISTRADOR).hasAnyRole("ADMINISTRADOR")
				.anyRequest().authenticated();
	}
	

}
