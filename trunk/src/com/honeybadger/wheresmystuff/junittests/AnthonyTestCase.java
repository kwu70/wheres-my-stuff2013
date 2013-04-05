package com.honeybadger.wheresmystuff.junittests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.honeybadger.wheresmystuff.support.Login;
import com.honeybadger.wheresmystuff.support.Member;
import com.honeybadger.wheresmystuff.support.Security;
import com.honeybadger.wheresmystuff.views.LoginView;

public class AnthonyTestCase {
	private Member m;
	
	@Before
	public void setUp() throws Exception {
		m = new Member(Security.getCurrentID(), "example@example.com", "hello", "Bob");
		Security.addMember(m.getEmail(), m.getPassword());
	}

	@After
	public void tearDown() throws Exception {
		Security.removeMember(m.getEmail());
		m = null;
	}


	/**
	 * Run the boolean validate(String,String) method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testValidate_1()
		throws Exception {
		Login fixture = new Login(new LoginView());
		Security.addMember(m.getEmail(), m.getPassword());
		String email = "";
		String password = null;

		boolean result = fixture.validate(email, password);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.RuntimeException: Stub!
		//       at android.content.Context.<init>(Context.java:4)
		//       at android.content.ContextWrapper.<init>(ContextWrapper.java:5)
		//       at android.view.ContextThemeWrapper.<init>(ContextThemeWrapper.java:5)
		//       at android.app.Activity.<init>(Activity.java:6)
		//       at com.honeybadger.wheresmystuff.views.AddItemActivity.<init>(AddItemActivity.java:21)
		assertTrue(result);
	}

	/**
	 * Run the boolean validate(String,String) method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testValidate_2()
		throws Exception {
		Login fixture = new Login(new LoginView());
		String email = "example@example.com";
		String password = "hello";

		boolean result = fixture.validate(email, password);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.RuntimeException: Stub!
		//       at android.content.Context.<init>(Context.java:4)
		//       at android.content.ContextWrapper.<init>(ContextWrapper.java:5)
		//       at android.view.ContextThemeWrapper.<init>(ContextThemeWrapper.java:5)
		//       at android.app.Activity.<init>(Activity.java:6)
		//       at com.honeybadger.wheresmystuff.views.AddItemActivity.<init>(AddItemActivity.java:21)
		assertTrue(result);
	}

	/**
	 * Run the boolean validate(String,String) method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testValidate_3()
		throws Exception {
		Login fixture = new Login(new LoginView());
		String email = "";
		String password = "";

		boolean result = fixture.validate(email, password);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.RuntimeException: Stub!
		//       at android.content.Context.<init>(Context.java:4)
		//       at android.content.ContextWrapper.<init>(ContextWrapper.java:5)
		//       at android.view.ContextThemeWrapper.<init>(ContextThemeWrapper.java:5)
		//       at android.app.Activity.<init>(Activity.java:6)
		//       at com.honeybadger.wheresmystuff.views.AddItemActivity.<init>(AddItemActivity.java:21)
		assertTrue(result);
	}

	/**
	 * Run the boolean validate(String,String) method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testValidate_4()
		throws Exception {
		Login fixture = new Login(new LoginView());
		String email = "";
		String password = "";

		boolean result = fixture.validate(email, password);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.RuntimeException: Stub!
		//       at android.content.Context.<init>(Context.java:4)
		//       at android.content.ContextWrapper.<init>(ContextWrapper.java:5)
		//       at android.view.ContextThemeWrapper.<init>(ContextThemeWrapper.java:5)
		//       at android.app.Activity.<init>(Activity.java:6)
		//       at com.honeybadger.wheresmystuff.views.AddItemActivity.<init>(AddItemActivity.java:21)
		assertTrue(result);
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(AnthonyTestCase.class);
	}

}
