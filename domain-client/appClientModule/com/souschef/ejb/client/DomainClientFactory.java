package com.souschef.ejb.client;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ibm.rmi.javax.rmi.PortableRemoteObject;
import com.souschef.domain.client.ComponentManager;
import com.souschef.domain.client.ComponentManagerRemote;
import com.souschef.domain.client.RecypeManager;
import com.souschef.domain.client.RecypeManagerRemote;

public class DomainClientFactory {
	private ComponentManager componentManager;
	private RecypeManager recypeManager;
	InitialContext context ;
	
	public DomainClientFactory() throws NamingException {
		Properties properties = new Properties();
		/* The SSL was disabled in the development server * 
		properties.put("com.ibm.SSL.ConfigURL","file:/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/properties/ssl.client.props");
		properties.put("com.ibm.CORBA.ConfigURL","file:/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/properties/sas.client.props");
		/* */
		
		/* Authentication properties */
		properties.put("com.ibm.CORBA.loginUserid", "wasadmin");
		properties.put("com.ibm.CORBA.loginPassword", "vasorange#1");
		properties.put("com.ibm.CORBA.loginSource", "properties");
		/* */
		
		/* *
		properties.put("com.ibm.CORBA.Debug","true");
		properties.put("com.ibm.CORBA.CommTrace", "true");
		/* */
		properties.put("java.naming.factory.initial", "com.ibm.websphere.naming.WsnInitialContextFactory");
		properties.put("java.naming.provider.url", "corbaloc:iiop:localhost:9100");	
		context = new InitialContext(properties);
	}
	

	public ComponentManager getComponentManager() throws ClassCastException, NamingException {
		if (componentManager == null) {
			/*
			 	Short form remote interfaces and homes
			 		https://www.ibm.com/support/knowledgecenter/en/SSAW57_8.0.0/com.ibm.websphere.nd.doc/info/ae/ae/cejb_bindingsejbfp.html 		 
			 */
			PortableRemoteObject portableRemoteObject = new PortableRemoteObject();
			
			/*
			 	Generate stubs
			 		https://www.ibm.com/support/knowledgecenter/en/SSAW57_8.5.5/com.ibm.websphere.nd.doc/ae/rejb_3stubscmd2.html
			 */
			componentManager  = (ComponentManagerRemote) portableRemoteObject.narrow(context.lookup("com.souschef.domain.client.ComponentManagerRemote"), ComponentManagerRemote.class);
			
		}
		
		return componentManager;
	}
	

	public RecypeManager getRecypeManager() throws ClassCastException, NamingException {
		if (recypeManager == null) {
			/*
			 	Short form remote interfaces and homes
			 		https://www.ibm.com/support/knowledgecenter/en/SSAW57_8.0.0/com.ibm.websphere.nd.doc/info/ae/ae/cejb_bindingsejbfp.html 		 
			 */
			PortableRemoteObject portableRemoteObject = new PortableRemoteObject();
			
			/*
			 	Generate stubs
			 		https://www.ibm.com/support/knowledgecenter/en/SSAW57_8.5.5/com.ibm.websphere.nd.doc/ae/rejb_3stubscmd2.html
			 */
			recypeManager  = (RecypeManagerRemote) portableRemoteObject.narrow(context.lookup("com.souschef.domain.client.RecypeManagerRemote"), RecypeManagerRemote.class);
			
		}
		
		return recypeManager;
	}	
}
