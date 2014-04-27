package com.jinnova.smartpad;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CrossDomainFilter implements Filter {

	public static final String ACCESS_CONTROL_ALLOW_ORIGIN_PARAM = "Access-Control-Allow-Origin";
	private String allowOriginSite;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Do nothing
		this.allowOriginSite = filterConfig.getInitParameter(ACCESS_CONTROL_ALLOW_ORIGIN_PARAM);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN_PARAM, this.allowOriginSite);
        res.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        res.setHeader("Access-Control-Max-Age", "1209600");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// do nothing
	}

}