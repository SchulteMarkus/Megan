package com.megan.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.data.neo4j.core.GraphDatabase;

import com.megan.boot.MeganApplication;

public abstract class MeganDomainTest<T extends MeganApplication> {

	protected T application;

	@After
	public void destroyTestDatabase() {
		Assert.assertFalse(this.application.getSpringApplicationContext().getBean(GraphDatabase.class)
				.transactionIsRunning());

		this.application.shutdown();
		this.application = null;
	}

	public T getApplication() {
		return this.application;
	}

	protected abstract T getNewDomainApplication();

	@Before
	public void prepareTestDatabase() {
		this.application = this.getNewDomainApplication();
		this.application.init();
	}
}
