package com.souschef.domain.model;
/* *
Disable IIOP over SSL when testing in development servers.
	http://www-01.ibm.com/support/docview.wss?uid=swg21614221
	https://www.ibm.com/support/knowledgecenter/SSAW57_8.0.0/com.ibm.websphere.nd.multiplatform.doc/info/ae/ae/usec_inbound.html
Copy the thin client into local machine if you want to invoke session beans and jms via ioop.
	https://www.ibm.com/support/knowledgecenter/SSAW57_8.5.5/com.ibm.websphere.nd.iseries.doc/ae/tcli_ejbthinclient.html
	https://sys101.doloveyou.com/year/2015/articles/jar_files_needed_to_use_WebSphere_JMS_clent.html	
/* */

import java.util.List;
import javax.naming.NamingException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.souschef.domain.client.ComponentManager;
import com.souschef.domain.data.model.Component;
import com.souschef.domain.data.model.ComponentCategory;
import com.souschef.ejb.client.DomainClientFactory;

@FixMethodOrder(MethodSorters.JVM)
public class ComponentManagerTest {
	DomainClientFactory domainClientFactory;
	ComponentManager componentManager;
	public ComponentManagerTest() throws NamingException {
		domainClientFactory = new DomainClientFactory();
		componentManager = domainClientFactory.getComponentManager();
	}
	
	@Test
	public void clearAll() {
		List<ComponentCategory> categories = componentManager.allComponentCategories();
		
		for(ComponentCategory category: categories) {
			List<Component> components = componentManager.componentsByCategoryId(category.getId());
			System.out.printf("Category=%s\n",category.getName());
			for(Component component: components) {
				System.out.printf("\tComponent=%s\n",component.getName());
				componentManager.removeComponent(component.getId());
			}
			componentManager.removeComponentCategory(category.getId());
		}
	}
	
	/* */
	@Test
	public void createComponentCategories() {
		
		for(ComponentCategory componentCategory: DomainModelTestSuite.componentCategories) {			
			componentManager.saveComponentCategory(componentCategory);
		}
		
	}	
	
		
	
	@Test
	public void createComponents() {
		List<ComponentCategory> categories = componentManager.allComponentCategories();

		for(ComponentCategory category: categories) {
			try {
				Component components[] = DomainModelTestSuite.componentMap.get(category.getName());
				for(Component component: components) {
					component.setCategory(category);
					componentManager.saveComponent(component);		
					componentManager.findComponentByName(component.getName()).getId();
				}
			}catch(NullPointerException e) {
				throw new NullPointerException(category.getName());
			}
			
		}
	}
	

	
}
