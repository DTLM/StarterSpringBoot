package com.adx.SpringBootInit.bo.imple;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.adx.SpringBootInit.bo.IUsuarioBo;
import com.adx.SpringBootInit.dao.IUsuarioDao;
import com.adx.SpringBootInit.exception.SenhaIncorretaException;
import com.adx.SpringBootInit.exception.UsuarioExistsException;
import com.adx.SpringBootInit.exception.UsuarioNotFound;
import com.adx.SpringBootInit.modal.Usuario;
import com.adx.SpringBootInit.modal.dto.UsuarioDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioBo implements IUsuarioBo{
	
	private IUsuarioDao dao;
	
	private final BCryptPasswordEncoder encoder;

	@Override
	public Usuario create(UsuarioDto usuario) throws UsuarioExistsException {
		Usuario usu = dao.findByEmail(usuario.getEmail());
		if(usu == null) {
			usu = Usuario.builder()
					.nome(usuario.getNome())
					.email(usuario.getEmail())
					.senha(encoder.encode(usuario.getSenha()))
					.telefone(usuario.getTelefone())
					.role(usuario.getRole())
					.build();
			usu = dao.save(usu);
		} else {
			throw new UsuarioExistsException("Usúario com o email: " + usuario.getEmail() +" já cadastrado");
		}
		return usu;
	}

	@Override
	public Usuario update(UsuarioDto usuario) throws SenhaIncorretaException {
		Usuario usu = dao.findByEmail(usuario.getEmail());
		if(usu != null && usu.getSenha().equalsIgnoreCase(encoder.encode(usuario.getSenha()))) {
			usu = Usuario.builder().build();
		} else {
			throw new SenhaIncorretaException();
		}
		return usu;
	}

	@Override
	public void deletar(Long id) throws UsuarioNotFound {
		Optional<Usuario> usu = dao.findById(id);
		if(usu.isPresent()) {
			dao.deleteById(id);
		} else {
			throw new UsuarioNotFound();
		}
	}

	@Override
	public Usuario load(Long id) throws UsuarioNotFound {
		Optional<Usuario> usu = dao.findById(id);
		if(usu.isEmpty()) {
			throw new UsuarioNotFound();
		}
		return usu.get();
	}

	@Override
	public Usuario create(Usuario entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario edit(Usuario entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
