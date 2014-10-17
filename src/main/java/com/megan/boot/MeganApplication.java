package com.megan.boot;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.neo4j.repository.GraphRepositoryFactory;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.mapping.Neo4jMappingContext;

/**
 * Implement a child-class. Annotate Named, ApplicationScoped and Eager. Your application serves as Producer
 */
public abstract class MeganApplication {

	protected static GraphRepositoryFactory graphRepositoryFactory;

	protected ClassPathXmlApplicationContext springApplicationContext;

	/**
	 * See https://issues.apache.org/jira/browse/SHIRO-337 for why this exists.
	 *
	 * @return
	 */
	public static GraphRepositoryFactory getGraphRepositoryFactoryUglyAsHell() {
		return MeganApplication.graphRepositoryFactory;
	}

	public ClassPathXmlApplicationContext getSpringApplicationContext() {
		return this.springApplicationContext;
	}

	@PostConstruct
	public void init() {
		final ClassPathXmlApplicationContext applicationContextToSet = new ClassPathXmlApplicationContext(
				"spring/application-context.xml");
		final Neo4jTemplate neo4jTemplate = applicationContextToSet.getBean(Neo4jTemplate.class);
		final Neo4jMappingContext neo4jMappingContext = applicationContextToSet.getBean(Neo4jMappingContext.class);

		MeganApplication.graphRepositoryFactory = new GraphRepositoryFactory(neo4jTemplate, neo4jMappingContext);

		this.springApplicationContext = applicationContextToSet;
		Logger.getLogger(MeganApplication.class).info("Application started.");
	}

	@PreDestroy
	public void shutdown() {
		if (this.springApplicationContext != null) {
			this.springApplicationContext.close();
		}

		Logger.getLogger(MeganApplication.class).info("Server shutdown executed.");
	}

}
