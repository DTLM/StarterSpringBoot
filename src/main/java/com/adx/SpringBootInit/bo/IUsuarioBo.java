package com.adx.SpringBootInit.bo;

import com.adx.SpringBootInit.exception.SenhaIncorretaException;
import com.adx.SpringBootInit.exception.UsuarioExistsException;
import com.adx.SpringBootInit.exception.UsuarioNotFound;
import com.adx.SpringBootInit.modal.Usuario;
import com.adx.SpringBootInit.modal.dto.UsuarioDto;

public interface IUsuarioBo {

	public Usuario create(UsuarioDto usuario) throws UsuarioExistsException;
	
	public Usuario edit(UsuarioDto usuario) throws SenhaIncorretaException;
	
	public void deletar(Long id) throws UsuarioNotFound;
	
	public Usuario load(Long id);
}
