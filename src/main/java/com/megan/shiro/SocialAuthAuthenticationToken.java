package com.megan.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class SocialAuthAuthenticationToken implements AuthenticationToken, RememberMeAuthenticationToken {

	private static final long serialVersionUID = -2563134239264625082L;

	private final SocialAuthPrincipal principal;

	private boolean rememberMe = false;

	public SocialAuthAuthenticationToken(final SocialAuthPrincipal principal) {
		this.principal = principal;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public boolean isRememberMe() {
		return this.rememberMe;
	}

	public void setRememberMe(final boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public String toString() {
		return "SocialAuthAuthenticationToken [principal=" + this.principal + ", rememberMe=" + this.rememberMe + "]";
	}
}
