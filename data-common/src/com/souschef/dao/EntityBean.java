package com.souschef.dao;

import java.io.Serializable;

/**
 * <p>This generic class define the common features present in all entity beans</p>
 * @author rsolano
 *
 * @param <I> The Id (aka primary) key for this class.
 */
public abstract class EntityBean<I extends Serializable> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5140146102848361867L;
	
	public abstract I getId();
	public abstract void setId(I id);
	
	
}
