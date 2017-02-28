package com.souschef.http.servlet;

import java.util.List;

import javax.ejb.EJB;


import com.souschef.client.ClientException;
import com.souschef.domain.client.ComponentManager;
import com.souschef.domain.data.model.ComponentCategory;
import com.souschef.domain.data.model.Component;

/**
 * Servlet implementation class DomainServiceServlet
 */
public class DomainServiceServlet extends ServiceServlet {
	private static final long serialVersionUID = 1L;
	@EJB(beanName="ComponentManagerLocalBean")
	ComponentManager componentManager;
    

    @EndPoint(path="/component-category/all")
    public List<ComponentCategory> allCategories(RequestContext request) throws ClientException{
    	List<ComponentCategory> categories = componentManager.allComponentCategories();
    	return categories;
    }

    @EndPoint(path="/component/by-category/\\S+")
    public List<Component> componentsByCategory(RequestContext request) throws ClientException{
    	String uri = request.getUri();
    	String id  = uri.substring(uri.lastIndexOf('/')+1);
    	List<Component> categories = componentManager.componentsByCategoryId(id);
    	return categories;
    }    
    
    @EndPoint(path="/component/")
    public Component component(ComponentRequestContext request) throws ClientException{
    	componentManager.saveComponent(request.getBean());
    	return request.getBean();
    } 
    
}
