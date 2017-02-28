package com.souschef.domain.client;

import javax.ejb.Remote;
import javax.ejb.Stateless;
//import javax.persistence.PersistenceUnit;

@Stateless(name = "ComponentManagerRemoteBean", mappedName = "ComponentManagerRemoteBean")
@Remote(ComponentManagerRemote.class)

public class ComponentManagerRemoteBean  extends ComponentManagerImpl implements ComponentManagerRemote{
	
	
	
}
