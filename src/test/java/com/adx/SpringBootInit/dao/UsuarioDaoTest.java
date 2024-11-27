package com.adx.SpringBootInit.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import static org.mockito.Mockito.*;

import com.adx.SpringBootInit.config.ApplicationConfig;
import com.adx.SpringBootInit.enuns.RolesEnum;
import com.adx.SpringBootInit.exception.UsuarioNotFoundException;
import com.adx.SpringBootInit.modal.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles(value = "teste")
class UsuarioDaoTest {

	@Autowired
	private IUsuarioDao dao;

	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	private Usuario user;

	@BeforeEach
	public void setUp(){
		user = Usuario.builder().email("teste@gmail.com").senha(encoder.encode("123")).nome("teste").role(RolesEnum.USUARIO).build();
		dao.save(user);
	}

	@Test
	public void existsByEmail(){
		boolean result = dao.existsByEmail("teste@gmail.com");
		assertTrue(result);
	}

	@Test
	public void findByEmail(){
		assertDoesNotThrow(() ->{
			Usuario userAux = dao.findByEmail(user.getEmail()).orElseThrow(UsuarioNotFoundException::new);
			assertThat(userAux).isNotNull();
			assertEquals("teste", userAux.getNome());
		});
	}

}
