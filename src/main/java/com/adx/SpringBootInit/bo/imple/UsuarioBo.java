package com.adx.SpringBootInit.bo.imple;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.adx.SpringBootInit.bo.IUsuarioBo;
import com.adx.SpringBootInit.dao.IUsuarioDao;
import com.adx.SpringBootInit.exception.SenhaIncorretaException;
import com.adx.SpringBootInit.exception.UsuarioExistsException;
import com.adx.SpringBootInit.modal.Usuario;
import com.adx.SpringBootInit.modal.dto.UsuarioDto;
import com.adx.SpringBootInit.util.GenericServiceImpl;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioBo extends GenericServiceImpl<Usuario> implements IUsuarioBo{
	
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

}
