package com.souschef.domain.client;


import javax.persistence.EntityManager;

import com.souschef.client.ClientException;
import com.souschef.dao.DAOException;
import com.souschef.domain.data.dao.RecypeDAO;
import com.souschef.domain.data.model.Recype;

public class RecypeManagerImpl extends DAOWrapperClient implements RecypeManager{
	protected RecypeDAO recypeDAO;		
	
	
	public RecypeManagerImpl() {
		super(); //Superfluous
		try {
			recypeDAO = new RecypeDAO(getEntityManagerFactory());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public void saveRecype(Recype recype) throws ClientException {
		try {
			recypeDAO.persist(recype);			
		}catch(DAOException e) {
			throw new ClientException(e);
		}catch(IllegalStateException e){
			throw new ClientException(e);
		}finally {
			
		}		
	}

	@Override
	public Recype findRecypeById(String id) throws ClientException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			return recypeDAO.findById(entityManager, id);
		}catch(DAOException e){
			throw new ClientException(e);
		}		
	}

	@Override
	public void removeRecype(String id) throws ClientException {
		Recype recype;
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try {
			recype = recypeDAO.findById(entityManager, id);
			if(recype != null)
				recypeDAO.remove(entityManager, recype);
			
		}catch(DAOException e) {
			throw new ClientException(e);
		}catch(IllegalStateException e){
			throw new ClientException(e);
		}
		
	}

}
