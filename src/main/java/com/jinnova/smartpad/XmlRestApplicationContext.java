// Copyright (c) 2012 Health Market Science, Inc.
package com.jinnova.smartpad;

import org.springframework.beans.BeansException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class XmlRestApplicationContext extends XmlWebApplicationContext {
    private boolean active;
    
    public XmlRestApplicationContext(String[] locations) {
    	this.setConfigLocations(locations);
    }

	@Override
    protected Resource getResourceByPath(String path) {
        if (getServletContext() == null) {
            return new ClassPathResource(path);
        }
        
        return super.getResourceByPath(path);
    }
    
    @Override
    public synchronized void refresh() throws BeansException, IllegalStateException {
        if (!active) {
            super.refresh();
            active = true;
        }
    }
}
