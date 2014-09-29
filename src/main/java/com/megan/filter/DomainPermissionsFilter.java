package com.megan.filter;

import java.util.Arrays;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public abstract class DomainPermissionsFilter extends FacesAjaxAwareUserFilter {

	protected String getIdParam(final ServletRequest request, final String paramName) {
		// Care for "?userId=372&userId=372"
		final String[] companyIdParamsFromMap = request.getParameterMap().get(paramName);
		final String companyIdParams = Arrays.toString(companyIdParamsFromMap).replace("[", "").replace("]", "");
		final int commaIndex = companyIdParams.indexOf(",");
		return commaIndex == -1 ? companyIdParams : companyIdParams.substring(0, commaIndex);
	}

	protected abstract String getParamName();

	@Override
	protected boolean isAccessAllowed(final ServletRequest request, final ServletResponse response,
			final Object mappedValue) {
		boolean accessIsAllowed = super.isAccessAllowed(request, response, mappedValue);
		if (accessIsAllowed) {
			final String id = this.getIdParam(request, this.getParamName());
			accessIsAllowed = this.isAccessAllowed(id);
		}

		return accessIsAllowed;
	}

	protected abstract boolean isAccessAllowed(final String id);
}
