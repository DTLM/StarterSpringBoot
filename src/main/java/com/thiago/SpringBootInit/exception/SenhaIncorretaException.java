package com.thiago.SpringBootInit.exception;

public class SenhaIncorretaException extends Exception{
	public SenhaIncorretaException() {
		super("Senha incorreta.");
	}
}
