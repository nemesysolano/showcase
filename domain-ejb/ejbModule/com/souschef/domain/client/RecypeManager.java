package com.souschef.domain.client;

import com.souschef.client.ClientException;
import com.souschef.domain.data.model.Recype;

public interface RecypeManager {
	void saveRecype(Recype recype) throws ClientException;
	Recype findRecypeById(String id) throws ClientException;
	void removeRecype(String id) throws ClientException;	
}
