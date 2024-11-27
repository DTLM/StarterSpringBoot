package com.thiago.SpringBootInit.dao;

import com.thiago.SpringBootInit.modal.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioDao extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
	
	boolean existsByEmail(String email);
}
