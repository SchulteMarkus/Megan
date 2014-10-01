package com.megan.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.data.neo4j.aspects.core.NodeBacked;

public abstract class SocialAuthRealm extends AuthorizingRealm {

	private final String realmName = this.getClass().getSimpleName();

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) {
		assert token instanceof SocialAuthAuthenticationToken;

		try {
			final SocialAuthPrincipal socialAuthPrincipal = (SocialAuthPrincipal) token.getPrincipal();
			final SimplePrincipalCollection principals = new SimplePrincipalCollection(socialAuthPrincipal,
					this.realmName);

			this.getOrCreateUser(socialAuthPrincipal.getProviderId(), socialAuthPrincipal.getValidatedId());

			return new SimpleAuthenticationInfo(principals, null, this.realmName);
		} catch (final Exception e) {
			throw new IncorrectCredentialsException(e);
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
		final SocialAuthPrincipal principalCollection = (SocialAuthPrincipal) principals.getPrimaryPrincipal();

		final Set<Permission> permissions = new HashSet<Permission>();
		final NodeBacked user = this.getOrCreateUser(principalCollection.getProviderId(),
				principalCollection.getValidatedId());
		if (user != null) {
			permissions.add(new WildcardPermission("user:*:" + user.getNodeId()));
		}

		final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setObjectPermissions(permissions);
		return info;
	}

	/**
	 * Get user with this socialAuth-informations. If the user does not exist, create him before.
	 *
	 * @param providerId
	 * @param validatedId
	 * @return
	 */
	protected abstract NodeBacked getOrCreateUser(final String providerId, final String validatedId);
}
