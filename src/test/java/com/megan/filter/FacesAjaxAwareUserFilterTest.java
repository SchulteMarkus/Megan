package com.megan.filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

public class FacesAjaxAwareUserFilterTest {

	@Test
	public void redirectToLogin() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			IOException {
		final FacesAjaxAwareUserFilter filter = new FacesAjaxAwareUserFilter();
		final HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
		final ServletResponse res = Mockito.mock(HttpServletResponse.class, Mockito.RETURNS_DEEP_STUBS);

		@SuppressWarnings("rawtypes")
		final Class[] args = new Class[2];
		args[0] = ServletRequest.class;
		args[1] = ServletResponse.class;
		final Method method = FacesAjaxAwareUserFilter.class.getDeclaredMethod("redirectToLogin", args);
		method.setAccessible(true);

		method.invoke(filter, req, res);

		Mockito.when(req.getHeader("Faces-Request")).thenReturn("partial/ajax");
		method.invoke(filter, req, res);
	}
}
