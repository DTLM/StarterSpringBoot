package com.thiago.SpringBootInit.security;

import com.thiago.SpringBootInit.bo.IJwtBo;
import com.thiago.SpringBootInit.bo.IUsuarioBo;
import com.thiago.SpringBootInit.exception.UsuarioNotFoundException;
import com.thiago.SpringBootInit.modal.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Configuration
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final IJwtBo jwtBo;
    private final IUsuarioBo usuarioBo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String jwtToken;
        String email;

        if(header == null || header.isBlank() || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken = header.substring(7);
        if(!jwtToken.isBlank()){
            email = jwtBo.extractEmail(jwtToken);
            if(email != null && !email.isBlank() &&  SecurityContextHolder.getContext().getAuthentication() == null){
                Usuario user = null;
                try {
                    user = usuarioBo.findByEmail(email);
                } catch (UsuarioNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if(user != null && jwtBo.tokenIsValid(jwtToken, user)){
                    UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );
                    userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(userToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
