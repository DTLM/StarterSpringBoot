package com.thiago.SpringBootInit.util;

import com.thiago.SpringBootInit.exception.UsuarioNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public abstract class GenericCrudServiceImpl<T extends Serializable> implements GenericService<T>{

	private Class<T> classe;

	@Override
	public T create(T entity) throws Exception {
		return getDao().save(entity);
	}

	@Override
	public T load(Long id) throws Exception {
		Optional<T> result = getDao().findById(id);
		if(result.isPresent()){
			return result.get();
		}
		throw new UsuarioNotFoundException();
	}

	@Override
	public T edit(T entity) throws Exception {
		return getDao().save(entity);
	}

	@Override
	public void deletar(final Long id) throws Exception {
		T obj = load(id);
		getDao().delete(obj);
	}

	protected abstract JpaRepository<T, Long> getDao();
}
