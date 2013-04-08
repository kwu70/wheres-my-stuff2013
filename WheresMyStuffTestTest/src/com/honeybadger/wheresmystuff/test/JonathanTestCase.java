package com.honeybadger.wheresmystuff.test;

import com.honeybadger.wheresmystuff.support.Login;
import com.honeybadger.wheresmystuff.support.Member;
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
		private Member member;
	
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
			Security.addMember("test@test.com", "test");
			member = Security.getMember("test@test.com");
		}
		
		/**
		 * Tests the lockOut method by trying lockOut with member at 0 failed attempts
		 */
		public void testLockout0(){
			Login fixture = new Login(lv);
			Boolean result = fixture.lockOut(member);
			assertTrue(!result);
		}
		
		/**
		 * Tests the lockOut method by trying lockOut with member at 1 failed attempts
		 */
		public void testLockout1(){
			Login fixture = new Login(lv);
			member.setFailedAttempts(0);
			member.incFailedAttempts();
			Boolean result = fixture.lockOut(member);
			assertTrue(!result);
		}
		
		/**
		 * Tests the lockOut method by trying lockOut with member at 2 failed attempts
		 */
		public void testLockout2(){
			Login fixture = new Login(lv);
			member.setFailedAttempts(0);
			member.incFailedAttempts();
			member.incFailedAttempts();
			Boolean result = fixture.lockOut(member);
			assertTrue(!result);
		}
		
		/**
		 * Tests the lockOut method by trying lockOut with member at 3 failed attempts
		 */
		public void testLockout3(){
			Login login = new Login(lv);
			member.setFailedAttempts(0);
			member.incFailedAttempts();
			member.incFailedAttempts();
			member.incFailedAttempts();
			Boolean result = login.lockOut(member);
			assertTrue(result);
		}
		
		/**
		 * Tests the lockOut method by trying lockOut with member at 4 failed attempts
		 */
		public void testLockout4(){
			Login login = new Login(lv);
			member.setFailedAttempts(0);
			member.incFailedAttempts();
			member.incFailedAttempts();
			member.incFailedAttempts();
			member.incFailedAttempts();
			Boolean result = login.lockOut(member);
			assertTrue(!result);
		}
		

}
