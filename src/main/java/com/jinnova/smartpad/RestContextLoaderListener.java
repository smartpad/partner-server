// Copyright (c) 2012 Health Market Science, Inc.
package com.jinnova.smartpad;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author trungnguyen
 *
 */
public class RestContextLoaderListener extends ContextLoaderListener {
    private final XmlRestApplicationContext appContext;
    
    public RestContextLoaderListener(XmlRestApplicationContext appContext) {
        super(appContext);
        
        this.appContext = appContext;
    }
    
    @Override
    protected WebApplicationContext createWebApplicationContext(ServletContext sc) {
        appContext.setServletContext(sc);
        
        return super.createWebApplicationContext(sc);
    }
}
