package com.megan.backingbean;

import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.neo4j.graphdb.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.neo4j.core.GraphDatabase;

/**
 * @see https://stackoverflow.com/questions/26158945/
 */
@Named
@RequestScoped
public class SpringDataNeo4jHelperBean {

	@Inject
	@Named
	private ClassPathXmlApplicationContext springApplicationContext;

	private Transaction tx;

	@PreDestroy
	public void finishTransaction() {
		if (this.tx != null) {
			this.tx.success();
			this.tx.finish();
		}
	}

	public void startReadOnlyTransaction() {
		final GraphDatabase gDb = this.springApplicationContext.getBean(GraphDatabase.class);
		if (!gDb.transactionIsRunning()) {
			this.tx = gDb.beginTx();
		}
	}
}
