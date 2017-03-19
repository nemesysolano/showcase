package com.souschef.domain.data.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.souschef.dao.BasicDAO;
import com.souschef.dao.DAOException;
import com.souschef.domain.data.model.Ingredient;
import com.souschef.domain.data.model.Recype;


public class RecypeDAO extends BasicDAO<String, Recype> {

	public RecypeDAO(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}

	@Override
	public void persist(EntityManager entityManager, Recype entity) throws DAOException {
		List<Ingredient> ingredients = entity.getIngredients();
		
		if(ingredients == null || ingredients.size() == 0) {
			throw new DAOException(new NullPointerException("The Recype has no ingredient or the ingredients list is null"));
		}
		
		for(Ingredient ingredient: ingredients) {
			ingredient.setRecype(entity);
		}
		super.persist(entityManager, entity);
	}

}
