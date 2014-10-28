package com.megan.faces.application;

import java.io.IOException;
import java.util.Properties;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceWrapper;

import org.primefaces.application.PrimeResourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Give project's static files a version parameter to clear browser cache.
 *
 * TODO Appends version to primefaces-files twice "&v=5.0.11-UR&v=5.0.11-UR".
 *
 * @see https://stackoverflow.com/questions/18143383/how-to-use-jsf-versioning-for-resources-in-jar
 */
public class MeganStaticFilesResourceHandlerWrapper extends PrimeResourceHandler {

	public MeganStaticFilesResourceHandlerWrapper(final ResourceHandler wrapped) {
		super(wrapped);
	}

	@Override
	public Resource createResource(final String resourceName, final String libraryName) {

		if (!org.primefaces.util.Constants.LIBRARY.equalsIgnoreCase(libraryName)
				&& !"javax.faces".equalsIgnoreCase(libraryName) && resourceName != null
				&& (resourceName.endsWith(".css") || resourceName.endsWith(".js"))) {

			final Resource resource = super.createResource(resourceName, libraryName);
			if (resource == null) {
				return null;
			}

			final Logger logger = LoggerFactory.getLogger(MeganStaticFilesResourceHandlerWrapper.class.getName());
			logger.debug("createResource {} {} {}", resource, resourceName, libraryName);

			return new ResourceWrapper() {
				@Override
				public String getRequestPath() {
					final StringBuilder resultPath = new StringBuilder();
					final String originalRequestPath = super.getRequestPath();
					resultPath.append(originalRequestPath);

					try {
						final Properties properties = new Properties();
						properties.load(this.getClass().getClassLoader().getResourceAsStream("git.properties"));
						final String gitCommitIdDescribe = properties.getProperty("git.commit.id.describe");

						if ("${git.commit.id.describe}".equalsIgnoreCase(gitCommitIdDescribe)) {
							logger.warn("git.properties was not filtered by maven / git-commit-id-plugin.");

						} else {
							resultPath.append(originalRequestPath.contains("?") ? "&" : "?");
							resultPath.append("gch=");
							resultPath.append(gitCommitIdDescribe);
						}

					} catch (final IOException | NullPointerException e) {
						logger.warn("git.properties was not loadable.", e);
					}

					return resultPath.toString();
				}

				@Override
				public Resource getWrapped() {
					return resource;
				}
			};

		} else {
			return super.createResource(resourceName, libraryName);

		}
	}
}
