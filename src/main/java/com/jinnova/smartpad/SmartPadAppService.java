package com.jinnova.smartpad;

import com.jinnova.smartpad.partner.SmartpadCommon;
import com.jinnova.smartpad.resource.AccountResource;
import com.jinnova.smartpad.resource.BranchResource;
import com.jinnova.smartpad.resource.CatalogItemResource;
import com.jinnova.smartpad.resource.CatalogResource;
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
    	//XmlRestApplicationContext appCtx = new XmlRestApplicationContext(new String[]{"/spring/webservice-context.xml", "/spring/webservice-security-context.xml"});
        // Add filter
        FilterBuilder fb = environment.addFilter(CrossDomainFilter.class, "/*");
        // Set name of filter
        fb.setName("crossDomainFilter");
        fb.setInitParam(CrossDomainFilter.ACCESS_CONTROL_ALLOW_ORIGIN_PARAM, configuration.getAllowOriginSite());
        // Servlet Listeners
        //environment.addServletListeners(new RestContextLoaderListener(appCtx));

        environment.addResource(new AccountResource());
        environment.addResource(new CatalogResource());
        environment.addResource(new BranchResource());
        environment.addResource(new CatalogItemResource());
        SmartpadCommon.initialize("localhost", null, "smartpad", "root", "", "", "");
        UserLoggedInManager.initialize();
    }

}
