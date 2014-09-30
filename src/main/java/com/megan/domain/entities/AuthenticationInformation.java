package com.megan.domain.entities;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public abstract class AuthenticationInformation {

	private Date created;

	@RelatedTo(type = "authorizes")
	protected User user;

	public DateTime getCreated() {
		return this.created == null ? null : new DateTime(this.created);
	}

	public User getUser() {
		return this.user;
	}

	public void setCreated(final DateTime created) {
		this.created = created.toDate();
	}

	public void setUser(final User user) {
		this.user = user;
	}
}
