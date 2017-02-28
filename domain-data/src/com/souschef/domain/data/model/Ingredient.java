package com.souschef.domain.data.model;

public class Ingredient {
	IngredientID ingredientID;
	public double amount;
	
	public Ingredient(Recype recipe, Component component, double amount) {
		this.ingredientID = new IngredientID(component, recipe);
		this.amount = amount;
	}

	
	public Ingredient(IngredientID ingredientID, double amount) {
		super();
		this.ingredientID = ingredientID;
		this.amount = amount;
	}


	public Ingredient() {
		
	}

	public IngredientID getIngredientID() {
		return ingredientID;
	}

	public void setIngredientID(IngredientID ingredientID) {
		this.ingredientID = ingredientID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
