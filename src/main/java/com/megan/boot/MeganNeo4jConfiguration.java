package com.megan.boot;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.aspects.support.node.Neo4jNodeBacking;
import org.springframework.data.neo4j.aspects.support.relationship.Neo4jRelationshipBacking;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.support.GraphDatabaseServiceFactoryBean;
import org.springframework.data.neo4j.support.node.NodeEntityStateFactory;

public abstract class MeganNeo4jConfiguration extends Neo4jConfiguration {

	@Bean
	public GraphDatabaseService graphDatabaseService() {
		return super.getGraphDatabaseService();
	}

	@PostConstruct
	public void initGraphDatabaseService() {
		final Properties neo4jConfigProperties = new Properties();
		try {
			neo4jConfigProperties.load(MeganNeo4jConfiguration.class.getClassLoader().getResourceAsStream(
					"neo4j.properties"));

			final String databaseServiceName = neo4jConfigProperties
					.getProperty("spring-datasource-graph_database_service_name");
			Logger.getLogger(MeganNeo4jConfiguration.class).trace("databaseServiceName: " + databaseServiceName);

			if ("graphDatabaseService-embedded".equals(databaseServiceName)) {
				final String datasourceUrl = neo4jConfigProperties.getProperty("neo4j-datasource-url");
				Logger.getLogger(MeganNeo4jConfiguration.class).trace("datasourceUrl: " + datasourceUrl);

				final GraphDatabaseServiceFactoryBean graphDatabaseServiceFactoryBean = new GraphDatabaseServiceFactoryBean(
						datasourceUrl);
				this.setGraphDatabaseService(graphDatabaseServiceFactoryBean.getObject());

			} else if ("graphDatabaseService-impermanent".equals(databaseServiceName)) {
				this.setGraphDatabaseService(new TestGraphDatabaseFactory().newImpermanentDatabase());

			} else {
				Logger.getLogger(MeganNeo4jConfiguration.class).warn(
						"Unknown databaseServiceName '" + databaseServiceName + "'.");

			}
		} catch (final Exception e) {
			Logger.getLogger(MeganNeo4jConfiguration.class).error(e);
		}

		Logger.getLogger(MeganNeo4jConfiguration.class).info(
				"Database '" + this.getGraphDatabaseService() + "' is initialized.");
	}

	@Bean
	public Neo4jNodeBacking neo4jNodeBacking() throws Exception {
		final Neo4jNodeBacking aspect = Neo4jNodeBacking.aspectOf();
		aspect.setTemplate(this.neo4jTemplate());
		final NodeEntityStateFactory entityStateFactory = this.nodeEntityStateFactory();
		aspect.setNodeEntityStateFactory(entityStateFactory);

		return aspect;
	}

	@Bean
	public Neo4jRelationshipBacking neo4jRelationshipBacking() throws Exception {
		final Neo4jRelationshipBacking aspect = Neo4jRelationshipBacking.aspectOf();
		aspect.setTemplate(this.neo4jTemplate());
		aspect.setRelationshipEntityStateFactory(this.relationshipEntityStateFactory());

		return aspect;
	}
}
