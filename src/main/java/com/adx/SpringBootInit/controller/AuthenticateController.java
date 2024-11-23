package com.adx.SpringBootInit.controller;

import com.adx.SpringBootInit.bo.IAuthenticationBo;
import com.adx.SpringBootInit.exception.SenhaIncorretaException;
import com.adx.SpringBootInit.exception.UsuarioNotFoundException;
import com.adx.SpringBootInit.modal.dto.Error;
import com.adx.SpringBootInit.modal.dto.UsuarioDto;
import com.adx.SpringBootInit.modal.dto.UsuarioResponse;
import com.adx.SpringBootInit.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
@Tag(name = "Authenticação", description = "Classe de login e logout")
@RequiredArgsConstructor
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class AuthenticateController {

    private final IAuthenticationBo authenticationBo;

    @PostMapping("/login")
    @Operation(summary = "realização de login do usuário, onde receberá o token de acesso.",description = "endpoint de login e aquisição do token JWT", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Senha incorreta.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
    })
    public ResponseEntity login(@io.swagger.v3.oas.annotations.parameters.RequestBody @RequestBody UsuarioDto usuarioDto){
        try {
            UsuarioResponse usuarioResponse = authenticationBo.login(usuarioDto);
            return ResponseEntity.ok().body(usuarioResponse);
        }catch (SenhaIncorretaException e){
            return new ResponseEntity<>(Error.builder().error(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }catch (UsuarioNotFoundException e){
            return new ResponseEntity<>(Error.builder().error(e.getMessage()).build(),HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(Error.builder().error(e.getMessage()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
