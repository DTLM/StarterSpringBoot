package com.thiago.SpringBootInit.service;


import com.thiago.SpringBootInit.service.impl.UsuarioBo;
import com.thiago.SpringBootInit.repository.IUsuarioDao;
import com.thiago.SpringBootInit.enuns.RolesEnum;
import com.thiago.SpringBootInit.exception.SenhaIncorretaException;
import com.thiago.SpringBootInit.exception.UsuarioExistsException;
import com.thiago.SpringBootInit.exception.UsuarioNotFoundException;
import com.thiago.SpringBootInit.modal.Usuario;
import com.thiago.SpringBootInit.modal.dto.UsuarioDto;
import com.thiago.SpringBootInit.modal.dto.UsuarioResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
	@Autowired
	private BCryptPasswordEncoder encoder;
	private Usuario user;
	private UsuarioDto userDto;
	
	@BeforeAll
	public void setUp() {
		user = Usuario.builder().email("teste@gmail.com").senha("123").nome("teste").telefone(null).role(RolesEnum.USUARIO).id(1L).build();
		userDto = UsuarioDto.builder().email("teste1@gmail.com").senha("123").nome("teste").telefone(null).role(RolesEnum.USUARIO).id(1L).build();
	}

	@Test
	@Order(1)
	void createUser() throws UsuarioExistsException {
		when(dao.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		when(dao.save(Mockito.any())).thenReturn(user);
		
		UsuarioResponse novo = bo.create(userDto);
		
		verify(dao,times(1)).findByEmail(Mockito.anyString());
		verify(dao,times(1)).save(Mockito.any());
	}
	
	@Test
	@Order(2)
	void createEmailDuplicadoException() {
		when(dao.findByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
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
		when(dao.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		when(dao.save(Mockito.any())).thenReturn(user);

		assertDoesNotThrow(() -> bo.create(userDto));
	}
	
	@Test
	void editar() throws SenhaIncorretaException, UsuarioNotFoundException, UsuarioExistsException {
		user.setSenha("$2y$10$eaguTaM7wkTicJqr8go.M.Pnxj54lVlIFKXYcIFfkdhZ7CUxl8bF2");
		when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		when(dao.existsByEmail(Mockito.anyString())).thenReturn(false);
		when(dao.save(Mockito.any())).thenReturn(user);

		UsuarioResponse result = bo.update(userDto);

		assertNotNull(result);
		verify(dao,times(1)).findById(Mockito.anyLong());
		verify(dao,times(1)).existsByEmail(Mockito.anyString());
		verify(dao,times(1)).save(Mockito.any());
	}
	
	@Test
	void editarSenhaIncorreta() {
		userDto.setSenha("4567");
		when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		assertThrows(SenhaIncorretaException.class, () ->{
			bo.update(userDto);
		});
	}
	
	@Test
	void editarClienteNaoExiste() {
		when(dao.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(UsuarioNotFoundException.class, () ->{
			bo.update(userDto);
		});
	}
	@Test
	void editarClienteEmailExiste() {
		user.setSenha("$2y$10$eaguTaM7wkTicJqr8go.M.Pnxj54lVlIFKXYcIFfkdhZ7CUxl8bF2");
		when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		when(dao.existsByEmail(Mockito.anyString())).thenReturn(true);
		userDto.setEmail("user2@gmail.com");
		userDto.setSenha("123");

		assertThrows(UsuarioExistsException.class, () ->{
			bo.update(userDto);
		});
	}


	@Test
	void carregarUsuario() throws Exception {
//		Arranjo
		when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
//		teste
		assertDoesNotThrow(() -> this.bo.load(userDto.getId()));
		Usuario retorno = this.bo.load(userDto.getId());
//		verificação
		assertNotNull(retorno);
		assertTrue(new ReflectionEquals(user).matches(retorno));
		verify(dao,times(2)).findById(userDto.getId());
	}
	
	@Test
	void carregarUsuarioNaoEncontrado() {
		when(dao.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(UsuarioNotFoundException.class, () ->{
			bo.load(userDto.getId());
		});
	}
	
	@Test
	void deletar() throws Exception {
		doReturn(user).when(bo).load(Mockito.anyLong());
		bo.deletar(user.getId());
		verify(dao, times(1)).delete(user);
	}
	
	@Test
	void deletarClienteNaoExiste() {
		assertThrows(UsuarioNotFoundException.class, ()->{
			bo.deletar(user.getId());
		});
	}
}
