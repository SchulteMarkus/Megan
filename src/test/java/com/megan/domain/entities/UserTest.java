package com.megan.domain.entities;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {

	@Test
	public void testToString() {
		final User user = new User();
		final String toString = user.toString();
		Assert.assertNotNull(toString);
	}
}
