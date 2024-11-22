package com.adx.SpringBootInit.bo;

import com.adx.SpringBootInit.exception.SenhaIncorretaException;
import com.adx.SpringBootInit.exception.UsuarioNotFoundException;
import com.adx.SpringBootInit.modal.dto.UsuarioDto;
import com.adx.SpringBootInit.modal.dto.UsuarioResponse;

public interface IAuthenticationBo {
    UsuarioResponse login(UsuarioDto usuarioDto) throws UsuarioNotFoundException, SenhaIncorretaException;
}
