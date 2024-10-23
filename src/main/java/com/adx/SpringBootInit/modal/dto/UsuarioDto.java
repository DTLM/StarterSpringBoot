package com.adx.SpringBootInit.modal.dto;

import com.adx.SpringBootInit.enuns.RolesEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {

	private Long id;
	private String nome;
	@NonNull
	private String senha;
	private String senhaNova;
	@NonNull
	private String email;
	private String telefone;
	private RolesEnum role;
}
