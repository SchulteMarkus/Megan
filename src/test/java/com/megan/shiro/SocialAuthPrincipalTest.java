package com.megan.shiro;

import org.junit.Assert;
import org.junit.Test;

public class SocialAuthPrincipalTest {

	private static final String providerId = "providerIdValue";

	private static final String validatedId = "validatedIdValue";

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
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(null, SocialAuthPrincipalTest.validatedId);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(null, SocialAuthPrincipalTest.validatedId);

		Assert.assertEquals(auth1, auth2);
		Assert.assertEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals5() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(SocialAuthPrincipalTest.providerId,
				SocialAuthPrincipalTest.validatedId);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(SocialAuthPrincipalTest.providerId,
				SocialAuthPrincipalTest.validatedId);

		Assert.assertEquals(auth1, auth2);
		Assert.assertEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals6() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(SocialAuthPrincipalTest.providerId, null);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(null, SocialAuthPrincipalTest.validatedId);

		Assert.assertNotEquals(auth1, auth2);
		Assert.assertNotEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals7() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(SocialAuthPrincipalTest.providerId,
				SocialAuthPrincipalTest.validatedId);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(null, SocialAuthPrincipalTest.validatedId);

		Assert.assertNotEquals(auth1, auth2);
		Assert.assertNotEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals8() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(SocialAuthPrincipalTest.providerId,
				SocialAuthPrincipalTest.validatedId + "suffix");
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(SocialAuthPrincipalTest.providerId,
				SocialAuthPrincipalTest.validatedId);

		Assert.assertNotEquals(auth1, auth2);
		Assert.assertNotEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals9() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(SocialAuthPrincipalTest.providerId,
				SocialAuthPrincipalTest.validatedId);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal("prefix" + SocialAuthPrincipalTest.providerId,
				SocialAuthPrincipalTest.validatedId);

		Assert.assertNotEquals(auth1, auth2);
		Assert.assertNotEquals(auth1.hashCode(), auth2.hashCode());
	}
}
