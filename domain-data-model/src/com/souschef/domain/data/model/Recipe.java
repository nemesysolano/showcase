package com.souschef.domain.data.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.souschef.dao.EntityBean;

@Entity
@Table(name = "RECYPE")
public class Recype  extends EntityBean<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5739588212741820240L;

	@Id
	@Column(name="ID")		
	private String id;
	
	@Column(name="NAME")
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //Unidirectional one to many.
	@JoinColumn(name="RECYPE_ID", referencedColumnName="ID")
	private List<Ingredient> ingredients;
	
	@Column(name="PRICE")
	private double price;
	
	@Column(name="PHOTO")
	private byte[] photo;
	
	public Recype() {	
		
	}
		
	
	private void init(String name, double price, byte[] photo, List<Ingredient> ingredients) {
		this.id = UUID.randomUUID().toString().replace(Constants.DASH, Constants.EMPTY_STRING);
		this.ingredients = ingredients;
		this.name = name;
		this.price = price;
		this.photo = photo;		
	}
	
	
	public Recype(String name, double price, File file, List<Ingredient> ingredients) throws IOException {
		FileInputStream input = null;
		byte photo[];
		
		try {
			input = new FileInputStream(file);
			photo = new byte[input.available()];
			input.read(photo);
			init(name, price, photo, ingredients);
		}finally {
			if(input != null)
				try {input.close(); } catch(IOException e) {}
		}
	}
	
	public Recype(String name, double price, File file) throws IOException {
		FileInputStream input = null;
		byte photo[];
		
		try {
			input = new FileInputStream(file);
			photo = new byte[input.available()];
			input.read(photo);
			init(name, price, photo, new ArrayList<Ingredient>());
		}finally {
			if(input != null)
				try {input.close(); } catch(IOException e) {}
		}
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
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	
}
