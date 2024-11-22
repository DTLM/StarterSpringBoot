package com.adx.SpringBootInit.bo;

import com.adx.SpringBootInit.exception.EmptyDataException;
import com.adx.SpringBootInit.exception.SenhaIncorretaException;
import com.adx.SpringBootInit.exception.UsuarioExistsException;
import com.adx.SpringBootInit.exception.UsuarioNotFoundException;
import com.adx.SpringBootInit.modal.Usuario;
import com.adx.SpringBootInit.modal.dto.UsuarioDto;
import com.adx.SpringBootInit.modal.dto.UsuarioResponse;
import com.adx.SpringBootInit.util.GenericService;

public interface IUsuarioBo extends GenericService<Usuario>{

	UsuarioResponse create(UsuarioDto usuario) throws UsuarioExistsException;
	
	UsuarioResponse update(UsuarioDto usuario) throws SenhaIncorretaException, UsuarioNotFoundException, UsuarioExistsException;

	Usuario findByEmail(String email) throws UsuarioNotFoundException;

}
