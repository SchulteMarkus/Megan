package com.megan.filter;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class NoCachingFilterTest {

	private NoCachingFilter filter;

	@Test
	public void destroy() {
		this.filter.destroy();
	}

	@Test
	public void doFilter_noResource() throws IOException, ServletException {
		final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("company/entry.xhtml");
		final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		final FilterChain filterChain = Mockito.mock(FilterChain.class);

		this.filter.doFilter(request, response, filterChain);

		Mockito.verify(response, Mockito.times(2)).setHeader(Matchers.any(String.class), Matchers.any(String.class));
		Mockito.verify(response, Mockito.times(2)).setDateHeader(Matchers.any(String.class), Matchers.any(Long.class));
	}

	@Test
	public void doFilter_resource() throws IOException, ServletException {
		final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn(ResourceHandler.RESOURCE_IDENTIFIER + "/test.js");
		final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		final FilterChain filterChain = Mockito.mock(FilterChain.class);

		this.filter.doFilter(request, response, filterChain);

		Mockito.verify(response, Mockito.never()).setHeader(Matchers.any(String.class), Matchers.any(String.class));
		Mockito.verify(response, Mockito.never()).setDateHeader(Matchers.any(String.class), Matchers.any(Long.class));
	}

	@Test
	public void init() {
		this.filter.init(Mockito.mock(FilterConfig.class));
	}

	@Before
	public void setupFilter() {
		this.filter = new NoCachingFilter();
	}
}
