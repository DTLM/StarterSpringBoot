package com.adx.SpringBootInit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adx.SpringBootInit.modal.Usuario;

@Repository
public interface IUsuarioDao extends JpaRepository<Usuario, Long>{

	public Usuario findByEmail(String email);
	public boolean exitsByEmail(String email);
}
