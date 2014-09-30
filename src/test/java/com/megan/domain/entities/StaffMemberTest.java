package com.megan.domain.entities;

import org.junit.Assert;
import org.junit.Test;

import com.megan.domain.entities.StaffMember;

public class StaffMemberTest {

	@Test
	public void testToString() {
		final StaffMember coworker = new StaffMember();
		Assert.assertNotNull(coworker.toString());
	}
}
