package com.adx.SpringBootInit.bo;

import com.adx.SpringBootInit.exception.SenhaIncorretaException;
import com.adx.SpringBootInit.exception.UsuarioExistsException;
import com.adx.SpringBootInit.exception.UsuarioNotFound;
import com.adx.SpringBootInit.modal.Usuario;
import com.adx.SpringBootInit.modal.dto.UsuarioDto;
import com.adx.SpringBootInit.util.GenericService;

public interface IUsuarioBo extends GenericService<Usuario>{

	Usuario create(UsuarioDto usuario) throws UsuarioExistsException;
	
	Usuario update(UsuarioDto usuario) throws SenhaIncorretaException, UsuarioNotFound;
}
