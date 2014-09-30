package com.megan.domain.entities;

import org.springframework.data.neo4j.annotation.Indexed;

public class SocialAuthAuthenticationInformation extends AuthenticationInformation {

	@Indexed
	private String socialAuthProviderId;

	@Indexed
	// @NotNull
	private String socialAuthValidatedId;

	public String getSocialAuthProviderId() {
		return this.socialAuthProviderId;
	}

	public String getSocialAuthValidatedId() {
		return this.socialAuthValidatedId;
	}

	public void setSocialAuthProviderId(final String socialAuthProviderId) {
		if (socialAuthProviderId != null) {
			this.socialAuthProviderId = socialAuthProviderId.toLowerCase();
		}
	}

	public void setSocialAuthValidatedId(final String socialAuthValidatedId) {
		if (socialAuthValidatedId != null) {
			this.socialAuthValidatedId = socialAuthValidatedId.toLowerCase();
		}
	}

	@Override
	public String toString() {
		return "SocialAuthAuthenticationInformation [socialAuthProviderId=" + this.socialAuthProviderId
				+ ", socialAuthValidatedId=" + this.socialAuthValidatedId + ", userId="
				+ (this.user == null ? "-no user-" : this.user.getNodeId()) + ", getCreated()=" + this.getCreated()
				+ ", id=" + this.getNodeId() + "]";
	}
}
