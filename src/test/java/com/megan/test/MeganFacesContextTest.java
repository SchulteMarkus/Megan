package com.megan.test;

import java.util.Collection;
import java.util.HashSet;

import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.LifecycleUtils;
import org.apache.shiro.util.ThreadState;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;

import com.megan.boot.MeganApplication;
import com.megan.shiro.MeganSocialAuthRealm;
import com.megan.shiro.SocialAuthCredentialsMatcher;

/**
 * @see https://shiro.apache.org/testing.html
 * @author markus
 */
public abstract class MeganFacesContextTest<T extends MeganApplication> extends MeganDomainTest<T> {

	private FacesContext context;

	private ThreadState subjectThreadState;

	private void clearSubject() {
		this.doClearSubject();
	}

	private ThreadState createThreadState(final Subject subject) {
		return new SubjectThreadState(subject);
	}

	private void doClearSubject() {
		if (this.subjectThreadState != null) {
			this.subjectThreadState.clear();
			this.subjectThreadState = null;
		}
	}

	protected ServletRequest getMockedHttpServletRequest() {
		final ServletRequest request = Mockito.mock(HttpServletRequest.class);
		return request;
	}

	protected ServletResponse getMockedHttpServletResponse() {
		final ServletResponse response = Mockito.mock(HttpServletResponse.class);
		return response;
	}

	private org.apache.shiro.mgt.SecurityManager getSecurityManager() {
		return SecurityUtils.getSecurityManager();
	}

	protected abstract MeganSocialAuthRealm getSocialAuthRealm();

	public void login(final String principal, final String password) {
		final Subject subject = SecurityUtils.getSubject();
		final UsernamePasswordToken token = new UsernamePasswordToken(principal, password);

		subject.login(token);
		Assert.assertTrue(subject.isAuthenticated());
	}

	@Before
	public void mockFacesContext() {
		this.context = FacesContextMocker.mockFacesContext();
	}

	@After
	public void relelaseFacesContext() {
		if (this.context != null) {
			this.context.release();
		}
	}

	private void setSecurityManager(final org.apache.shiro.mgt.SecurityManager securityManager) {
		SecurityUtils.setSecurityManager(securityManager);
	}

	private void setSubject(final Subject subject) {
		this.clearSubject();
		this.subjectThreadState = this.createThreadState(subject);
		this.subjectThreadState.bind();
	}

	@Before
	public void setupSecurityManager() {
		final MeganSocialAuthRealm socialAuthRealm = this.getSocialAuthRealm();
		socialAuthRealm.setCredentialsMatcher(new SocialAuthCredentialsMatcher());

		final Collection<Realm> realms = new HashSet<>();
		realms.add(socialAuthRealm);

		final org.apache.shiro.mgt.SecurityManager securityManager = new DefaultSecurityManager(realms);
		this.setSecurityManager(securityManager);

		final Subject subjectUnderTest = new Subject.Builder(this.getSecurityManager()).buildSubject();
		this.setSubject(subjectUnderTest);
	}

	@After
	public void tearDownShiro() {
		this.doClearSubject();
		try {
			final org.apache.shiro.mgt.SecurityManager securityManager = this.getSecurityManager();
			LifecycleUtils.destroy(securityManager);
		} catch (final UnavailableSecurityManagerException e) {
			LoggerFactory.getLogger("FacesContextTest.tearDownShiro").debug("UnavailableSecurityManagerException", e);
		}
		this.setSecurityManager(null);
	}

	@After
	public void tearDownSubject() {
		this.clearSubject();
	}
}
