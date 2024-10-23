package com.adx.SpringBootInit.bo;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adx.SpringBootInit.bo.imple.UsuarioBo;
import com.adx.SpringBootInit.dao.IUsuarioDao;

@ExtendWith(value = SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class UsuarioBoTest {
	
	@SpyBean
	private UsuarioBo bo;
	
	@MockBean
	private IUsuarioDao dao;
	
	@BeforeAll
	public void setUp() {
		
	}

}
