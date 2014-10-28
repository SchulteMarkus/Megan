package com.megan.faces.application;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.megan.faces.application.MeganStaticFilesResourceHandlerWrapper;

public class MeganStaticFilesResourceHandlerWrapperTest {

	private MeganStaticFilesResourceHandlerWrapper resourceHandlerWrapper;

	@Test
	public void ownResource_null() {
		final Resource resource = this.resourceHandlerWrapper.createResource("some-resources.js", null);
		Assert.assertNull(resource);
	}

	@Test
	public void primefacesResource_null() {
		final Resource resource = this.resourceHandlerWrapper.createResource("some-resources.js",
				org.primefaces.util.Constants.LIBRARY);
		Assert.assertNull(resource);
	}

	@Before
	public void setupResourceHandler() {
		final ResourceHandler resourceHandler = Mockito.mock(ResourceHandler.class);
		this.resourceHandlerWrapper = new MeganStaticFilesResourceHandlerWrapper(resourceHandler);
	}
}
