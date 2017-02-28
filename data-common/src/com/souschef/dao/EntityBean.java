package com.souschef.dao;

import java.io.Serializable;

public abstract class EntityBean<I extends Serializable> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5140146102848361867L;
	
	public abstract I getId();
	public abstract void setId(I id);
	
	
}
