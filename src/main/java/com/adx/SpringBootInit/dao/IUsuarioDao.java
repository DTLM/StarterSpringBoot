package com.adx.SpringBootInit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adx.SpringBootInit.modal.Usuario;

import java.util.Optional;

@Repository
public interface IUsuarioDao extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
	boolean exitsByEmail(String email);
}
