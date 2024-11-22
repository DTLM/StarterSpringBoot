package com.adx.SpringBootInit.security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity()
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
@SecurityScheme(type = SecuritySchemeType.HTTP, name = SecurityConfig.SECURITY, bearerFormat = "JWT",scheme = "bearer")
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider provider;
    public static final String SECURITY = "bearerAuth";
    private static final String[] swaggerPaths = {
            "/v3/api-docs/**",
            "/swagger-ui/index.html",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(autorize -> autorize
                        .requestMatchers("/usuario/**").hasAnyRole("Administrador","Usúario")
                        .requestMatchers("/authenticate/**").hasAnyRole("Administrador","Usúario")
                        .requestMatchers("/authenticate/login").permitAll()
                        .requestMatchers(swaggerPaths).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(provider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
