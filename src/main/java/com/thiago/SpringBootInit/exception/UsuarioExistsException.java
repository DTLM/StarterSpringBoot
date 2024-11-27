package com.thiago.SpringBootInit.exception;

public class UsuarioExistsException extends Exception{
	
	public UsuarioExistsException(String mensagem) {
		super(mensagem);
	}
}
