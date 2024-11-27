package com.thiago.SpringBootInit.util;

import java.io.Serializable;

public interface GenericService<T extends Serializable> {

	T create(final T entity)throws Exception;
	
	T load(Long id) throws Exception;
	
	T edit(final T entity) throws Exception;
	
	void deletar(final Long id) throws Exception;
}
