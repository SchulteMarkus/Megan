package com.megan.filter;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoCachingFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;
		final String uri = httpRequest.getRequestURI();

		if (!uri.contains(ResourceHandler.RESOURCE_IDENTIFIER)) {
			httpResponse.setHeader("Cache-Control", "no-cache");
			httpResponse.setDateHeader("Expires", 0);
			httpResponse.setHeader("Pragma", "no-cache");
			httpResponse.setDateHeader("Max-Age", 0);
		}
	}

	@Override
	public void init(final FilterConfig filterConfig) {
	}
}
