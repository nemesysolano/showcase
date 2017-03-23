package com.souschef.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.souschef.domain.client.ComponentManager;
import com.souschef.domain.client.RecypeManager;
import com.souschef.domain.data.model.Component;
import com.souschef.domain.data.model.Ingredient;
import com.souschef.domain.data.model.Recype;
import com.souschef.ejb.client.DomainClientFactory;



@FixMethodOrder(MethodSorters.JVM)
public class RecypeManagerTest {
	Logger logger = LogManager.getLogger(RecypeManagerTest.class);
	
	DomainClientFactory domainClientFactory;
	RecypeManager recypeManager;
	ComponentManager componentManager;
	
	public RecypeManagerTest() throws NamingException {
		domainClientFactory = new DomainClientFactory();
		recypeManager = domainClientFactory.getRecypeManager();
		componentManager = domainClientFactory.getComponentManager();
	}
	
	@Test
	public void testAddRecypes() {
		Recype recypes[] = DomainModelTestSuite.recypes;
		
		logger.debug("testAddRecypes START");
		
		for(Recype recype: recypes) {
			List<Ingredient> ingredients = recype.getIngredients();
			List<Ingredient> ingredientWithComponentIds = new ArrayList<Ingredient>();
			
			for(Ingredient ingredient: ingredients) {
				Component component = componentManager.findComponentByName(ingredient.getComponent().getName());
				ingredient.setComponent(component);
				ingredientWithComponentIds.add(ingredient);
			}
			recype.setIngredients(ingredientWithComponentIds);
			recypeManager.saveRecype(recype);
			logger.debug(String.format("Added recype for '%s'", recype.getName()));
		}
		
		logger.debug("testAddRecypes END");
	}
	
	@Test
	public void testFetchAllRecypes() {
		List<Recype> recypes = recypeManager.allRecypes();
		for(Recype recype: recypes) {
			
		}
	}
}
