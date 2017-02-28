package com.souschef.domain.data.model;

import java.io.Serializable;


public class IngredientID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9190117827114887998L;
	private Component component;
	private Recype recipe;
	
	public IngredientID() {
		
	}
	
	
	
	public IngredientID(Component component, Recype recipe) {
		super();
		this.component = component;
		this.recipe = recipe;
	}

	public Component getComponent() {
		return component;
	}
	public void setComponent(Component component) {
		this.component = component;
	}
	public Recype getRecipe() {
		return recipe;
	}
	public void setRecipe(Recype recipe) {
		this.recipe = recipe;
	}

	
}
