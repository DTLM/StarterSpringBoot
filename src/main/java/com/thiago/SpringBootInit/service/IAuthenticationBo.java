package com.thiago.SpringBootInit.service;

import com.thiago.SpringBootInit.exception.SenhaIncorretaException;
import com.thiago.SpringBootInit.exception.UsuarioNotFoundException;
import com.thiago.SpringBootInit.modal.dto.UsuarioDto;
import com.thiago.SpringBootInit.modal.dto.UsuarioResponse;

public interface IAuthenticationBo {
    UsuarioResponse login(UsuarioDto usuarioDto) throws UsuarioNotFoundException, SenhaIncorretaException;
}
