package com.adx.SpringBootInit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adx.SpringBootInit.bo.IUsuarioBo;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

	private final IUsuarioBo service;
	
	@GetMapping("teste")
	public ResponseEntity hello() {
		return ResponseEntity.ok("ola mundo");
	}
}
