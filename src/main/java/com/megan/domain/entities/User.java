package com.megan.domain.entities;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class User {

	@RelatedTo(type = "authorizes", direction = Direction.INCOMING)
	private AuthenticationInformation authInfo;

	@RelatedTo(type = "isStaffMember")
	private StaffMember staffMember;

	public AuthenticationInformation getAuthInfo() {
		return this.authInfo;
	}

	public StaffMember getStaffMember() {
		return this.staffMember;
	}

	public boolean isStaffMember() {
		return this.staffMember != null;
	}

	public void setAuthInfo(final AuthenticationInformation authInfo) {
		this.authInfo = authInfo;
	}

	@Override
	public String toString() {
		return "User [id=" + this.getNodeId() + ", authInfoId="
				+ (this.authInfo == null ? "-no authInfo-" : this.authInfo.getNodeId()) + ", staffMemberId="
				+ (this.staffMember == null ? "-no staffMember-" : this.staffMember.getNodeId()) + "]";
	}
}
