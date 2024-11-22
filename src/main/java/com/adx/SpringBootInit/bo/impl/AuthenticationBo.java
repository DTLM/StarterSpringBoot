package com.adx.SpringBootInit.bo.impl;

import com.adx.SpringBootInit.bo.IAuthenticationBo;
import com.adx.SpringBootInit.bo.IUsuarioBo;
import com.adx.SpringBootInit.exception.SenhaIncorretaException;
import com.adx.SpringBootInit.exception.UsuarioNotFoundException;
import com.adx.SpringBootInit.modal.Usuario;
import com.adx.SpringBootInit.modal.dto.UsuarioDto;
import com.adx.SpringBootInit.modal.dto.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationBo implements IAuthenticationBo {

    private final IUsuarioBo usuarioBo;
    private final AuthenticationManager authenticationManager;
    private final JwtBo jwtBo;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UsuarioResponse login(UsuarioDto usuarioDto) throws UsuarioNotFoundException, SenhaIncorretaException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioDto.getEmail(), usuarioDto.getSenha()));
        Usuario usu = usuarioBo.findByEmail(usuarioDto.getEmail());
        if(usu != null && !encoder.matches(usuarioDto.getSenha(),usu.getSenha())) {
            throw new SenhaIncorretaException();
        }
        return UsuarioResponse.builder().email(usu.getEmail()).id(usu.getId()).token(jwtBo.gerarToken(null,usu)).build();
    }
}
