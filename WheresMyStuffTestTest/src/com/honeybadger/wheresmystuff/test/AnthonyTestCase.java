package com.honeybadger.wheresmystuff.test;

import com.honeybadger.wheresmystuff.support.Login;
import com.honeybadger.wheresmystuff.support.Security;
import com.honeybadger.wheresmystuff.views.LoginView;

/**
 * JUnit Test for Validate Method in Login
 * This class requires that the value being inputted is not null because it doesn't null check
 * So I only need to do 4 test cases
 * @author Anthony Stange
 *
 */
public class AnthonyTestCase extends android.test.ActivityInstrumentationTestCase2<LoginView> {

		private LoginView lv;
	
		//required constructor for Testing
		public AnthonyTestCase(){
			super(LoginView.class);
		}
		
		//Gets the login view activity and adds the member
		//with the email example@example.com
		//and password hello
		@Override
		public void setUp(){
			lv = getActivity();
			Security.addMember("example@example.com", "hello");
		}
		
		//This test checks to see if validate returns false
		//if the inputted username is wrong, but the password is
		//right.
		public void testValidate1(){
			Login fixture = new Login(lv);
			Boolean result = fixture.validate("", "hello");
			assertTrue(!result);
		}

		//This test checks to see if validate returns false
		//if the inputted username is right, but the password is
		//wrong.
		public void testValidate2(){
			Login fixture = new Login(lv);
			Boolean result = fixture.validate("example@example.com", "");
			assertTrue(!result);
		}

		//This test checks to see if validate returns false
		//if the inputted username is wrong, but the password is
		//wrong.
		public void testValidate3(){
			Login fixture = new Login(lv);
			Boolean result = fixture.validate("", "");
			assertTrue(!result);
		}

		//This test checks to see if validate returns true
		//if the inputted username is right, but the password is
		//right.
		public void testValidate4(){
			Login fixture = new Login(lv);
			Boolean result = fixture.validate("example@example.com", "hello");
			assertTrue(result);
		}
}
