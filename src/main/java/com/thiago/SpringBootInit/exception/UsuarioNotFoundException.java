package com.thiago.SpringBootInit.exception;

public class UsuarioNotFoundException extends Exception{
	
	public UsuarioNotFoundException() {
		super("Usúario não encontrado.");
	}

}
