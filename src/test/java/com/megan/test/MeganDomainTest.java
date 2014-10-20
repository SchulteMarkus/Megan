package com.megan.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.data.neo4j.core.GraphDatabase;

import com.megan.boot.MeganApplication;

public abstract class MeganDomainTest<T extends MeganApplication> {

	protected T application;

	@Before
	public void bootApplication() {
		this.application = this.getNewDomainApplication();
		this.application.init();
	}

	protected abstract T getNewDomainApplication();

	@After
	public void shutdownApplication() {
		Assert.assertFalse(this.application.getSpringApplicationContext().getBean(GraphDatabase.class)
				.transactionIsRunning());

		this.application.shutdown();
		this.application = null;
	}
}
