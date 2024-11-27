package com.thiago.SpringBootInit.bo;

import com.thiago.SpringBootInit.modal.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface IJwtBo {
    String extractEmail(String token);

    boolean tokenIsValid(String token, Usuario user);

    String gerarToken(Map<String, Object> dados, UserDetails userDetails);
}
