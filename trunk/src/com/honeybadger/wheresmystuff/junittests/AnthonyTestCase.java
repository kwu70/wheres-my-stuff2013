package com.honeybadger.wheresmystuff.junittests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.honeybadger.wheresmystuff.support.DatabaseHandlerMembers;
import com.honeybadger.wheresmystuff.support.Login;
import com.honeybadger.wheresmystuff.support.Member;

public class AnthonyTestCase {
	private Login lg;
	private static DatabaseHandlerMembers dbM;
	private Member m;
	
	@Before
	public void setUp() throws Exception {
		m = new Member(dbM.getCurrentMemberID(), "example@example.com", "hello", "Bob");
		dbM.addMember(m);
	}

	@After
	public void tearDown() throws Exception {
		dbM.deleteMember(m);
	}

	@Test
	public void testValidate() {
		Assert.assertEquals("The validate method in login works", true, lg.validate("example@example.com", "hello"));	
	}

}
