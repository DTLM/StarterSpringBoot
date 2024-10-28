package com.adx.SpringBootInit.bo;



import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adx.SpringBootInit.bo.impl.UsuarioBo;
import com.adx.SpringBootInit.dao.IUsuarioDao;
import com.adx.SpringBootInit.enuns.RolesEnum;
import com.adx.SpringBootInit.exception.NotFoundException;
import com.adx.SpringBootInit.exception.SenhaIncorretaException;
import com.adx.SpringBootInit.exception.UsuarioExistsException;
import com.adx.SpringBootInit.exception.UsuarioNotFound;
import com.adx.SpringBootInit.modal.Usuario;
import com.adx.SpringBootInit.modal.dto.UsuarioDto;

@ExtendWith(value = SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles(value = "teste")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioBoTest {
	
	@SpyBean
	private UsuarioBo bo;
	
	@MockBean
	private IUsuarioDao dao;
	
	private Usuario user;
	private UsuarioDto userDto;
	
	@BeforeAll
	public void setUp() {
		user = Usuario.builder().email("teste@gmail.com").senha("123").nome("teste").telefone(null).role(RolesEnum.USUARIO).build();
		userDto = UsuarioDto.builder().email("teste@gmail.com").senha("123").nome("teste").telefone(null).role(RolesEnum.USUARIO).build();
	}
	
	@Test
	@Order(1)
	void createUser() throws UsuarioExistsException {
		Mockito.when(dao.findByEmail(Mockito.anyString())).thenReturn(null);
		Mockito.when(dao.save(Mockito.any())).thenReturn(user);
		
		Usuario novo = bo.create(userDto);
		
		verify(dao,times(1)).findByEmail(Mockito.anyString());
		assertTrue(new ReflectionEquals(user).matches(novo));
	}
	
	@Test
	@Order(2)
	void createEmailDuplicadoException() {
		Mockito.when(dao.findByEmail(Mockito.anyString())).thenReturn(user);
		assertThrows(UsuarioExistsException.class, () -> {
			bo.create(userDto);
		});
		Throwable exception = catchThrowable(() -> {
			bo.create(userDto);
		});
		assertThat(exception).isInstanceOf(UsuarioExistsException.class).hasMessage("Usúario com o email: "  + userDto.getEmail() + " já cadastrado");
	}
	
	@Test
	@Order(3)
	void createNaoLancaExcecao() {
		assertDoesNotThrow(() -> bo.create(userDto));
	}
	
	@Test
	void editar() {
		fail("ainda não implementado.");
	}
	
	@Test
	void editarSenhaIncorreta() {
		userDto.setSenha("4567");
		Mockito.when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		assertThrows(SenhaIncorretaException.class, () ->{
			bo.update(userDto);
		});
	}
	
	@Test
	void editarClienteNaoExiste() {
		Mockito.when(dao.findById(Mockito.anyLong())).thenReturn(null);
		assertThrows(UsuarioNotFound.class, () ->{
			bo.update(userDto);
		});
	}
	

	@Test
	void carregarUsuario() {
		fail("ainda não implementado.");
	}
	
	@Test
	void carregarUsuarioNaoEncontrado() {
		Mockito.when(dao.findById(Mockito.anyLong())).thenReturn(null);
		assertThrows(NotFoundException.class, () ->{
			bo.load(userDto.getId());
		});
	}
	
	@Test
	void deletar() {
		fail("ainda não implementado.");
	}
	
	@Test
	void deletarClienteNaoExiste() {
		fail("ainda não implementado.");
	}
}
