package com.adx.SpringBootInit.controller;

import com.adx.SpringBootInit.bo.IUsuarioBo;
import com.adx.SpringBootInit.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuario", description = "Endpoints de serviços de usuario.")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UsuarioController {

	private final IUsuarioBo service;

	// criar login (gerar token)

	// criar deslogar (eliminar security context holder

	@GetMapping("teste")
	@Operation(summary = "retorna hello", description = "retorna ola mundo em string.", method = "GET")
    @ApiResponses(
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    )
	public ResponseEntity hello() {
		return ResponseEntity.ok("ola mundo");
	}
}
