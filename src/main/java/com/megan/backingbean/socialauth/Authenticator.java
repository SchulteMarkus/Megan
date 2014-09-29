package com.megan.backingbean.socialauth;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.cdi.SocialAuth;
import org.hibernate.validator.constraints.NotEmpty;
import org.omnifaces.util.Faces;
import org.slf4j.LoggerFactory;

import com.megan.shiro.SocialAuthAuthenticationToken;
import com.megan.shiro.SocialAuthPrincipal;

/**
 * https://code.google.com/p/socialauth/wiki/CDISample
 */
@SessionScoped
@Named("socialauthenticator")
public class Authenticator implements Serializable {

	private static final long serialVersionUID = -159078463349251273L;

	@NotEmpty
	private String openID;

	private boolean rememberMe = true;

	@Inject
	@Named("socialauth")
	private SocialAuth socialauth;

	public String getOpenID() {
		return this.openID;
	}

	public boolean getRememberMe() {
		return this.rememberMe;
	}

	public void setOpenID(final String openID) {
		this.openID = openID;
	}

	public void setRememberMe(final boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public void updateId(final String providerName) {
		final String viewUrl = "/social-auth-callback";
		this.socialauth.setViewUrl(viewUrl);

		if ("openid".equals(providerName.toLowerCase().trim())) {
			Logger.getLogger(Authenticator.class).trace("Using OpenId " + this.openID);
			this.socialauth.setId(this.openID);
		} else {
			Logger.getLogger(Authenticator.class).trace("Using " + providerName);
			this.socialauth.setId(providerName);
		}
	}

	public String verify() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login nicht erfolgreich.",
				"Login nicht erfolgreich.");
		String redirect = null;
		try {
			this.socialauth.connect();
			final Profile socialProfile = this.socialauth.getProfile();
			final SocialAuthPrincipal socialAuthPrincipal = new SocialAuthPrincipal(socialProfile.getProviderId(),
					socialProfile.getValidatedId());

			final SocialAuthAuthenticationToken token = new SocialAuthAuthenticationToken(socialAuthPrincipal);
			token.setRememberMe(this.rememberMe);
			SecurityUtils.getSubject().login(token);

			// If there was a previous request, use that url as redirect-destination.
			final SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
			if (savedRequest != null) {
				try {
					Faces.redirect(savedRequest.getRequestUrl());
					return "";
				} catch (final IOException e) {
					LoggerFactory.getLogger(Authenticator.class).error("IOException", e);
				}
			}

			redirect = "/";
			message = new FacesMessage("Login erfolgreich.");

		} catch (final IncorrectCredentialsException e) {
			LoggerFactory.getLogger(Authenticator.class).debug(e.toString());
		} catch (final AuthenticationException e) {
			LoggerFactory.getLogger(Authenticator.class).warn("AuthenticationException", e);
		} catch (final Exception e) {
			LoggerFactory.getLogger(Authenticator.class).error("Exception", e);
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		return redirect;
	}
}
