package com.megan.shiro;

import java.io.Serializable;
import java.util.Objects;

public class SocialAuthPrincipal implements Serializable {

	private static final long serialVersionUID = 1262557116135574569L;

	private final String providerId;

	private final String validatedId;

	public SocialAuthPrincipal(final String providerId, final String validatedId) {
		this.providerId = providerId;
		this.validatedId = validatedId;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof SocialAuthPrincipal) {
			final SocialAuthPrincipal objT = (SocialAuthPrincipal) obj;
			if (this.providerId == null && this.validatedId == null && objT.getProviderId() == null
					&& objT.getValidatedId() == null) {
				return true;
			}
			return (this.providerId == null || this.providerId.equals(objT.getProviderId()))
					&& this.validatedId != null && this.validatedId.equals(objT.getValidatedId());
		}

		return false;
	}

	public String getProviderId() {
		return this.providerId;
	}

	public String getValidatedId() {
		return this.validatedId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.providerId, this.validatedId);
	}

	@Override
	public String toString() {
		return "SocialAuthPrincipal [providerId=" + this.providerId + ", validatedId=" + this.validatedId + "]";
	}
}
