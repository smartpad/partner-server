package com.jinnova.smartpad;

import com.jinnova.smartpad.partner.SmartpadCommon;
import com.jinnova.smartpad.resource.AccountResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

@SuppressWarnings("rawtypes")
public class SmartPadAppService extends Service<SmartPadConfiguration> {
    public static void main(String[] args) throws Exception {
        new SmartPadAppService().run(args);
    }

    @Override
    public void initialize(Bootstrap bootstrap) {
        bootstrap.setName("smart-pad");
    }

    @Override
    public void run(SmartPadConfiguration configuration,
                    Environment environment) {
        environment.addResource(new AccountResource());
        SmartpadCommon.initialize("localhost", null, "smartpad", "root", "");
    }

}