package com.adx.SpringBootInit.util;

public interface GenericService<T> {

	T create(final T entity)throws Exception;
	
	T load(Long id) throws Exception;
	
	T edit(final T entity) throws Exception;
	
	void deletar(final Long id) throws Exception;
}
