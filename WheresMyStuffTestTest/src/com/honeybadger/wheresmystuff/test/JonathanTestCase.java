package com.honeybadger.wheresmystuff.test;

import com.honeybadger.wheresmystuff.support.Login;
import com.honeybadger.wheresmystuff.support.Security;
import com.honeybadger.wheresmystuff.views.LoginView;

/**
 * JUnit Test for LockOut Method in Login
 * Lockout the member and check to see if the user is locked out.
 * Method checks to see if Member is locked out.
 * 
 * @author Jonathan Elliott
 *
 */
public class JonathanTestCase extends android.test.ActivityInstrumentationTestCase2<LoginView> {

		private LoginView lv;
	
		//required constructor for Testing
		public JonathanTestCase(){
			super(LoginView.class);
		}
		
		/**
		 * Gets the login view activity and adds the member 
		 * with the email test@test.com and password test
		 */
		@Override
		public void setUp(){
			lv = getActivity();
			Member member = new Member("test@test.com", "test");
			Security.addMember(member);
		}
		
		/**
		 * Tests the lockOut method by locking out the created Member
		 * and then trying to login with the Member.
		 */
		public void testLockout(){
			Login login = new Login(lv);
			login.lockOut(member);
			Boolean result = login.validate("test@test.com", "test");
			assertTrue(!result);
		}

}
