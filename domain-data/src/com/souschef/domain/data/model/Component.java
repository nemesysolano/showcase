package com.souschef.domain.data.model;


import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


import com.souschef.dao.EntityBean;

@Entity
@Table(name = "COMPONENT")
@NamedQueries(value={
		@NamedQuery(
		      name = "Component.byCategory",
		      query="SELECT c from Component c WHERE c.category = :category")
		}
	)
public class Component extends EntityBean<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3803978389701457498L;
	
	
	@Id
	@Column(name="ID")		
	private String id;
	
	
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="COMPONENT_CATEGORY_ID")
	private ComponentCategory category;
	
	@Column(name="NAME")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name="UNIT")
	private Unit packageUnit;
	
	@Column(name="PACKAGE_SIZE")
	private int packageSize; //how many package units ?
	
	@Column(name="STOCK")
	private double stock; //Actually, this field contains a packed decimal. 
	
	@Column(name="PHOTO")
	private byte[] photo;
	
	@Column(name="PRICE")
	private double price;
	
	public Component() {
		
	}
	
	public Component(String name, Unit packageUnit, int packageSize, int stock) {
		this(UUID.randomUUID().toString().replace(Constants.DASH, Constants.EMPTY_STRING), null, name, packageUnit, packageSize, stock);
	}
	
	public Component(String id, ComponentCategory category, String name, Unit packageUnit, int packageSize, int stock) {
		super();
		this.id = id;
		this.category = category;
		this.name = name;
		this.packageUnit = packageUnit;
		this.packageSize = packageSize;
		this.stock = stock;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ComponentCategory getCategory() {
		return category;
	}
	public void setCategory(ComponentCategory category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Unit getPackageUnit() {
		return packageUnit;
	}
	public void setPackageUnit(Unit packageUnit) {
		this.packageUnit = packageUnit;
	}
	public int getPackageSize() {
		return packageSize;
	}
	public void setPackageSize(int packageSize) {
		this.packageSize = packageSize;
	}
	public double getStock() {
		return stock;
	}
	public void setStock(double stock) {
		this.stock = stock;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	

	
}
