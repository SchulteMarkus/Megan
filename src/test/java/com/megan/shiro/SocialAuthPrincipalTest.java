package com.megan.shiro;

import org.junit.Assert;
import org.junit.Test;

public class SocialAuthPrincipalTest {

	private static final String PROVIDER_ID = "providerIdValue";

	private static final String VALIDATED_ID = "validatedIdValue";

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
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(null, SocialAuthPrincipalTest.VALIDATED_ID);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(null, SocialAuthPrincipalTest.VALIDATED_ID);

		Assert.assertEquals(auth1, auth2);
		Assert.assertEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals5() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(SocialAuthPrincipalTest.PROVIDER_ID,
				SocialAuthPrincipalTest.VALIDATED_ID);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(SocialAuthPrincipalTest.PROVIDER_ID,
				SocialAuthPrincipalTest.VALIDATED_ID);

		Assert.assertEquals(auth1, auth2);
		Assert.assertEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals6() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(SocialAuthPrincipalTest.PROVIDER_ID, null);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(null, SocialAuthPrincipalTest.VALIDATED_ID);

		Assert.assertNotEquals(auth1, auth2);
		Assert.assertNotEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals7() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(SocialAuthPrincipalTest.PROVIDER_ID,
				SocialAuthPrincipalTest.VALIDATED_ID);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(null, SocialAuthPrincipalTest.VALIDATED_ID);

		Assert.assertNotEquals(auth1, auth2);
		Assert.assertNotEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals8() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(SocialAuthPrincipalTest.PROVIDER_ID,
				SocialAuthPrincipalTest.VALIDATED_ID + "suffix");
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal(SocialAuthPrincipalTest.PROVIDER_ID,
				SocialAuthPrincipalTest.VALIDATED_ID);

		Assert.assertNotEquals(auth1, auth2);
		Assert.assertNotEquals(auth1.hashCode(), auth2.hashCode());
	}

	@Test
	public void equals9() {
		final SocialAuthPrincipal auth1 = new SocialAuthPrincipal(SocialAuthPrincipalTest.PROVIDER_ID,
				SocialAuthPrincipalTest.VALIDATED_ID);
		final SocialAuthPrincipal auth2 = new SocialAuthPrincipal("prefix" + SocialAuthPrincipalTest.PROVIDER_ID,
				SocialAuthPrincipalTest.VALIDATED_ID);

		Assert.assertNotEquals(auth1, auth2);
		Assert.assertNotEquals(auth1.hashCode(), auth2.hashCode());
	}
}
