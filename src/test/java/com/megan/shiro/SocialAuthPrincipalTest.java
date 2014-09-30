package com.megan.shiro;

import org.junit.Assert;
import org.junit.Test;

public class SocialAuthPrincipalTest {

	private final String providerId = "providerIdValue";

	private final String validatedId = "validatedIdValue";

	@Test
	public void equals1() {
		Assert.assertNotEquals(new SocialAuthPrincipal(null, null), null);
	}

	@Test
	public void equals2() {
		Assert.assertNotEquals(new SocialAuthPrincipal(null, null), "some obj");
	}

	@Test
	public void equals3() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(null, null);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(null, null);

		Assert.assertEquals(auth1, auth2);
		Assert.assertEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals4() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(null, this.validatedId);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(null, this.validatedId);

		Assert.assertEquals(auth1, auth2);
		Assert.assertEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals5() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(this.providerId, this.validatedId);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(this.providerId, this.validatedId);

		Assert.assertEquals(auth1, auth2);
		Assert.assertEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals6() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(this.providerId, null);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(null, this.validatedId);

		Assert.assertNotEquals(auth1, auth2);
		Assert.assertNotEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals7() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(this.providerId, this.validatedId);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(null, this.validatedId);

		Assert.assertNotEquals(auth1, auth2);
		Assert.assertNotEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals8() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(this.providerId, this.validatedId + "suffix");
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(this.providerId, this.validatedId);

		Assert.assertNotEquals(auth1, auth2);
		Assert.assertNotEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals9() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(this.providerId, this.validatedId);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal("prefix" + this.providerId, this.validatedId);

		Assert.assertNotEquals(auth1, auth2);
		Assert.assertNotEquals(auth1.hashCode(), auth2.hashCode());
	}
}
