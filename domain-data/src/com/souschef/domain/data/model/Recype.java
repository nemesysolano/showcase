package com.souschef.domain.data.model;

import java.io.Serializable;
import java.util.Set;

public class Recype implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5739588212741820240L;

	public String id;
	public String name;
	public Set<Ingredient> ingredients;
	public double price;
	byte[] Photo;
	
	public Recype() {		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public byte[] getPhoto() {
		return Photo;
	}

	public void setPhoto(byte[] photo) {
		Photo = photo;
	}
	
	
}
