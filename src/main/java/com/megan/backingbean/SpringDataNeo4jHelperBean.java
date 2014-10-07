package com.megan.backingbean;

import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.neo4j.graphdb.Transaction;
import org.springframework.data.neo4j.support.Neo4jTemplate;

/**
 * https://stackoverflow.com/questions/26158945/
 */
@Named
@RequestScoped
public class SpringDataNeo4jHelperBean {

	@Inject
	@Named
	private Neo4jTemplate neoTemplate;

	private Transaction tx;

	@PreDestroy
	public void finishTransaction() {
		if (this.tx != null) {
			this.tx.success();
			this.tx.finish();
		}
	}

	public void startReadOnlyTransaction() {
		if (!this.neoTemplate.getGraphDatabase().transactionIsRunning()) {
			this.tx = this.neoTemplate.getGraphDatabaseService().beginTx();
		}
	}
}
