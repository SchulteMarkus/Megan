package com.megan.provider;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.omnifaces.cdi.Eager;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.neo4j.support.Neo4jTemplate;

@Named
@ApplicationScoped
@Eager
public class Neo4jProvider {

	private static Neo4jTemplate neoTemplate;

	public static Neo4jTemplate getNeoTemplateUgly() {
		return Neo4jProvider.neoTemplate;
	}

	private ClassPathXmlApplicationContext applicationContext;

	@Named
	@Produces
	public Neo4jTemplate getNeoTemplate() {
		return Neo4jProvider.neoTemplate;
	}

	@PostConstruct
	public void init() {
		LoggerFactory.getLogger(Neo4jProvider.class).info("Starting to init neo4j-database.");

		if (Neo4jProvider.neoTemplate != null) {
			LoggerFactory.getLogger(Neo4jProvider.class).info(
					"Database[" + Neo4jProvider.neoTemplate.getGraphDatabaseService().getClass().getName()
							+ "] already initialized.");
			return;
		}

		final ClassPathXmlApplicationContext applicationContextToSet = new ClassPathXmlApplicationContext(
				"spring/applicationContext.xml");
		final Neo4jTemplate neoTemplateToSet = applicationContextToSet.getBean("template", Neo4jTemplate.class);

		Neo4jProvider.neoTemplate = neoTemplateToSet;
		this.applicationContext = applicationContextToSet;
		LoggerFactory.getLogger(Neo4jProvider.class).info(
				"Database[" + neoTemplateToSet.getGraphDatabaseService().getClass().getName() + "] started/connected.");
	}

	@PreDestroy
	public void shutdownDb() {
		if (Neo4jProvider.neoTemplate != null) {
			Neo4jProvider.neoTemplate.getGraphDatabaseService().shutdown();
		}
		Neo4jProvider.neoTemplate = null;

		if (this.applicationContext != null) {
			this.applicationContext.close();
		}

		LoggerFactory.getLogger(Neo4jProvider.class).info("Server shutdown executed.");
	}
}
