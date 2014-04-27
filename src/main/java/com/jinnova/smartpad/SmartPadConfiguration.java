package com.jinnova.smartpad;

import org.codehaus.jackson.annotate.JsonProperty;

import com.yammer.dropwizard.config.Configuration;

/**
 * An object representation of the YAML configuration file.
 * 
 */
public class SmartPadConfiguration extends Configuration {

    /*@NotNull
    @NotEmpty
    @JsonProperty
    private String appContextType;
    
    public String getAppContextType() {
        return appContextType;
    }*/
	@JsonProperty
    private String allowOriginSite;
	
	public String getAllowOriginSite() {
		return this.allowOriginSite;
	}
}