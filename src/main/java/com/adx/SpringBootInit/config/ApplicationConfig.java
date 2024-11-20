package com.adx.SpringBootInit.config;

import com.adx.SpringBootInit.dao.IUsuarioDao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final IUsuarioDao usuarioDao;

	@Bean
	public UserDetailsService userDetailsService(){
		return username -> usuarioDao.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " não encontrado."));
	}

	@Bean
	public BCryptPasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() throws UsernameNotFoundException{
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(encoder());
		authProvider.setUserDetailsService(userDetailsService());
		return authProvider;
	}
}
