package com.megan.filter;

import org.apache.shiro.SecurityUtils;

public class UdUserPermissionsFilter extends DomainPermissionsFilter {

	@Override
	protected String getParamName() {
		return "userId";
	}

	@Override
	protected boolean isAccessAllowed(final String id) {
		return SecurityUtils.getSubject().isPermitted("user:update:" + id);
	}
}
