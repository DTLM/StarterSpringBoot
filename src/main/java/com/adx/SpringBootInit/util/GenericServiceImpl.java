package com.adx.SpringBootInit.util;

import org.springframework.stereotype.Repository;

import com.adx.SpringBootInit.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;

@Repository
public class GenericServiceImpl<T> implements GenericService<T>{

	@PersistenceContext
	private EntityManager dao;
	
	private Class<T> classe;
	
	@Override
	public T create(T entity) throws Exception {
		dao.persist(entity);
		return entity;
	}

	@Override
	public T load(Long id) throws Exception {
		T obj = dao.find(classe, id);
		if(obj != null) {
			return obj;
		} else {
			throw new NotFoundException("Objeto da classe: "+ classe.getName() +" não encontrado.");
		}
	}

	@Override
	public T edit(T entity) throws Exception {
		return dao.merge(entity);
	}

	@Override
	public void deletar(Long id) throws Exception {
		T obj = load(id);
		if(obj != null) {
			dao.remove(obj);
		} else {
			throw new NotFoundException("Objeto não encontrado.");
		}
	}

}
