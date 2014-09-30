package com.megan.domain.entities;

import java.util.Date;

import org.joda.time.DateTime;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class StaffMember {

	// @NotNull
	private Date created;

	// @NotNull
	@RelatedTo(type = "isStaffMember", direction = Direction.INCOMING)
	private User user;

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

	@Override
	public String toString() {
		return "StaffMember [userId=" + (this.user == null ? "-no user-" : this.user.getNodeId()) + ", created="
				+ this.created + ", id=" + this.getNodeId() + "]";
	}
}
