package com.megan.test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public abstract class FacesContextMocker extends FacesContext {

	private static class Release implements Answer<Void> {
		@Override
		public Void answer(final InvocationOnMock invocation) {
			FacesContext.setCurrentInstance(null);
			return null;
		}
	}

	private static final Release RELEASE = new Release();

	public static FacesContext mockFacesContext() {
		final FacesContext context = Mockito.mock(FacesContext.class, Mockito.RETURNS_DEEP_STUBS);
		FacesContext.setCurrentInstance(context);

		final Map<String, Object> session = new HashMap<>();
		final Map<String, String> requestParameterMap = new HashMap<>();
		final ServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRemoteAddr()).thenReturn("127-testing-1");

		final Flash mockFlash = Mockito.mock(Flash.class);

		final ExternalContext ext = Mockito.mock(ExternalContext.class);
		Mockito.when(ext.getSessionMap()).thenReturn(session);
		Mockito.when(ext.getRequest()).thenReturn(request);
		Mockito.when(ext.getRequestParameterMap()).thenReturn(requestParameterMap);
		Mockito.when(ext.getFlash()).thenReturn(mockFlash);
		Mockito.when(ext.getRequestContextPath()).thenReturn("facestest");

		final UIViewRoot viewRoot = Mockito.mock(UIViewRoot.class);
		Mockito.when(viewRoot.getLocale()).thenReturn(new Locale("en"));

		Mockito.when(context.getExternalContext()).thenReturn(ext);
		Mockito.when(context.getViewRoot()).thenReturn(viewRoot);
		Mockito.doAnswer(FacesContextMocker.RELEASE).when(context).release();

		return context;
	}
}
