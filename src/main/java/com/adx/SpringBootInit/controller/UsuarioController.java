package com.adx.SpringBootInit.controller;

import com.adx.SpringBootInit.bo.IUsuarioBo;
import com.adx.SpringBootInit.bo.impl.UsuarioBo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

	private final IUsuarioBo service;

	@GetMapping("teste")
	public ResponseEntity hello() {
		return ResponseEntity.ok("ola mundo");
	}
}
