package com.megan.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.subject.SimplePrincipalCollection;

public class SocialAuthCredentialsMatcher implements CredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(final AuthenticationToken token, final AuthenticationInfo info) {
		if (token instanceof SocialAuthAuthenticationToken) {
			final SimplePrincipalCollection principalCollectionFromInfo = (SimplePrincipalCollection) info
					.getPrincipals().getPrimaryPrincipal();

			final SocialAuthPrincipal principalFromToken = (SocialAuthPrincipal) token.getPrincipal();
			return principalFromToken.equals(principalCollectionFromInfo.getPrimaryPrincipal());
		}

		return false;
	}
}
