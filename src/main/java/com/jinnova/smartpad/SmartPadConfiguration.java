package com.jinnova.smartpad;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import com.yammer.dropwizard.config.Configuration;

/**
 * An object representation of the YAML configuration file.
 * 
 */
public class SmartPadConfiguration extends Configuration {

    @NotNull
    @NotEmpty
    @JsonProperty
    private String appContextType;
    
    public String getAppContextType() {
        return appContextType;
    }
}