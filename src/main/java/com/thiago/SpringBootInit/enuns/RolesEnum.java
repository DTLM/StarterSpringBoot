package com.thiago.SpringBootInit.enuns;

import lombok.Getter;

@Getter
public enum RolesEnum {
	ADMINISTRADOR("Administrador"),
	USUARIO("Usúario");
	
	private final String name;
	
	private RolesEnum(String name) {
		this.name = name;
	}
}
