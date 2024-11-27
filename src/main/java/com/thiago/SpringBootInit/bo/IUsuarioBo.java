package com.thiago.SpringBootInit.bo;

import com.thiago.SpringBootInit.exception.SenhaIncorretaException;
import com.thiago.SpringBootInit.exception.UsuarioExistsException;
import com.thiago.SpringBootInit.exception.UsuarioNotFoundException;
import com.thiago.SpringBootInit.modal.Usuario;
import com.thiago.SpringBootInit.modal.dto.UsuarioDto;
import com.thiago.SpringBootInit.modal.dto.UsuarioResponse;
import com.thiago.SpringBootInit.util.GenericService;

public interface IUsuarioBo extends GenericService<Usuario>{

	UsuarioResponse create(UsuarioDto usuario) throws UsuarioExistsException;
	
	UsuarioResponse update(UsuarioDto usuario) throws SenhaIncorretaException, UsuarioNotFoundException, UsuarioExistsException;

	Usuario findByEmail(String email) throws UsuarioNotFoundException;

}
