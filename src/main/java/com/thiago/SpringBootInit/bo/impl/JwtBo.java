package com.thiago.SpringBootInit.bo.impl;

import com.thiago.SpringBootInit.bo.IJwtBo;
import com.thiago.SpringBootInit.modal.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtBo implements IJwtBo {

    @Value("${secret.key}")
    private String SECRET_KEY;

    @Override
    public String extractEmail(String token) {
        return getClaims(token, (claims) -> (String) claims.get("email"));
    }

    @Override
    public boolean tokenIsValid(String token, Usuario user) {
        String email = extractEmail(token);
        return (email.equalsIgnoreCase(user.getEmail())) && !tokenExpirado(token);
    }

    @Override
    public String gerarToken(Map<String, Object> dados, UserDetails userDetails) {
        return Jwts.builder()
                .claims(dados)
                .signWith(getSignature())
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (3600 * 24 * 1000))) // 1 hora x quantidade de horas desejada x 1000 milissegundos (para converser para milissegundos)
                .compact();
    }

    private boolean tokenExpirado(String token) {
        return getClaims(token,Claims::getExpiration).before(new Date());
    }

    public <T> T getClaims(String token, Function<Claims, T> resolver){
        final Claims claims = extractClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignature())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignature(){
        byte[] keyByte = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
