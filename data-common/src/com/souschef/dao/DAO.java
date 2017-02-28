package com.souschef.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

public abstract class DAO<I extends Serializable, E extends EntityBean<I>>  {
	protected Class<E> entityClass;
	protected Class<I> idClass;
		
	public abstract void persist(EntityManager entityManager, E entity) throws DAOException;
	public abstract void remove(EntityManager entityManager, E entity) throws DAOException;
	public abstract E findById(EntityManager entityManager, I key) throws DAOException;
		

	public abstract void persist(E entity) throws DAOException;
	public abstract void remove(E entity) throws DAOException;
	public abstract E findById(I key) throws DAOException;
	
}
