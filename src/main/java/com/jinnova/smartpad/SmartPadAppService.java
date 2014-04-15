package com.jinnova.smartpad;

import org.springframework.web.filter.DelegatingFilterProxy;

import com.jinnova.smartpad.partner.SmartpadCommon;
import com.jinnova.smartpad.resource.AccountResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.FilterBuilder;

/**
 * Drop wizard entry point class.
 *
 */
public class SmartPadAppService extends Service<SmartPadConfiguration> {
    public static void main(String[] args) throws Exception {
        new SmartPadAppService().run(args);
    }

    private SmartPadAppService() {
    }
    
    @Override
    public void initialize(Bootstrap<SmartPadConfiguration> bootstrap) {
        bootstrap.setName("smart-pad");
    }

    @Override
    public void run(SmartPadConfiguration configuration, Environment environment) {
    	//ApplicationContext parent = new ClassPathXmlApplicationContext(new String[]{"dropwizardSpringApplicationContext.xml"}, true);
    	
    	XmlRestApplicationContext appCtx = new XmlRestApplicationContext(new String[]{"/spring/webservice-context.xml", "/spring/webservice-security-context.xml"});
    	//appCtx.setParent(parent);
    	//appCtx.setConfigLocations();
    	//appCtx.refresh();
        
        //appContext.close();
    	// Filters
    	//FilterConfiguration filter = filterEntry.getValue();
        // Add filter
        FilterBuilder filterConfig = environment.addFilter(DelegatingFilterProxy.class, "/*");
        // Set name of filter
        filterConfig.setName("springSecurityFilterChain");

        // Servlet Listeners
        environment.addServletListeners(new RestContextLoaderListener(appCtx));

        environment.addResource(new AccountResource());
        SmartpadCommon.initialize();
    }

}