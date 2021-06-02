package br.pro.delfino.dscatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AppConfig {
	@Bean
	public BCryptPasswordEncoder codificarDeSenhas() {
		BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
		return codificador;
	}
	
	
	@Bean
	public JwtAccessTokenConverter conversorDoTokenDeAcesso() {
		JwtAccessTokenConverter conversor = new JwtAccessTokenConverter();
		conversor.setSigningKey("MY-JWT-SECRET");
		return conversor;
	}

	@Bean
	public JwtTokenStore armazenadorDoToken() {
		JwtTokenStore armazenador = new JwtTokenStore(conversorDoTokenDeAcesso());
		return armazenador;
	}
}
