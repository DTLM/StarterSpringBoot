package com.thiago.SpringBootInit.service.impl;

import com.thiago.SpringBootInit.service.IUsuarioBo;
import com.thiago.SpringBootInit.repository.IUsuarioDao;
import com.thiago.SpringBootInit.exception.SenhaIncorretaException;
import com.thiago.SpringBootInit.exception.UsuarioExistsException;
import com.thiago.SpringBootInit.exception.UsuarioNotFoundException;
import com.thiago.SpringBootInit.modal.Usuario;
import com.thiago.SpringBootInit.modal.dto.UsuarioDto;
import com.thiago.SpringBootInit.modal.dto.UsuarioResponse;
import com.thiago.SpringBootInit.util.GenericCrudServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioBo extends GenericCrudServiceImpl<Usuario> implements IUsuarioBo {
	
	private final IUsuarioDao dao;
	private final BCryptPasswordEncoder encoder;

	@Override
	protected IUsuarioDao getDao() {
		return dao;
	}

	@Override
	public UsuarioResponse create(UsuarioDto usuario) throws UsuarioExistsException {
		Optional<Usuario> usu = dao.findByEmail(usuario.getEmail());
		Usuario aux = null;
		if(usu.isEmpty()) {
			aux = Usuario.builder()
					.nome(usuario.getNome())
					.email(usuario.getEmail())
					.senha(encoder.encode(usuario.getSenha()))
					.telefone(usuario.getTelefone())
					.role(usuario.getRole())
					.build();
			aux = dao.save(aux);
		} else {
			throw new UsuarioExistsException("Usúario com o email: " + usuario.getEmail() +" já cadastrado");
		}
		return UsuarioResponse.builder().email(aux.getEmail()).id(aux.getId()).build();
	}

	@Override
	public UsuarioResponse update(UsuarioDto usuario) throws SenhaIncorretaException, UsuarioNotFoundException, UsuarioExistsException {
		Usuario usu = dao.findById(usuario.getId()).orElseThrow(UsuarioNotFoundException::new);
		if(usu != null && encoder.matches(usuario.getSenha(),usu.getSenha())) {
			if(dao.existsByEmail(usuario.getEmail()) && !usuario.getEmail().equals(usu.getEmail())){
				throw new UsuarioExistsException("Usúario com o email: " + usuario.getEmail() +" já cadastrado");
			}
			if(usuario.getNome()!= null && !usuario.getNome().isBlank()) {
				usu.setNome(usuario.getNome());
			}
			if(usuario.getSenhaNova()!= null && !usuario.getSenhaNova().isBlank()) {
				usu.setSenha(encoder.encode(usuario.getSenhaNova()));
			}
			if(usuario.getRole() != null) {
				usu.setRole(usuario.getRole());
			}
			if(usuario.getTelefone()!= null && !usuario.getTelefone().isBlank()) {
				usu.setTelefone(usuario.getTelefone());
			}
			usu.setEmail(usuario.getEmail());
			usu = dao.save(usu);
		} else {
			throw new SenhaIncorretaException();
		}
		return UsuarioResponse.builder().email(usu.getEmail()).id(usu.getId()).build();
	}

	@Override
	public Usuario findByEmail(String email) throws UsuarioNotFoundException {
		Optional<Usuario> user =  dao.findByEmail(email);
		if(user.isEmpty()){
			throw new UsuarioNotFoundException();
		}
		return user.get();
	}

}
