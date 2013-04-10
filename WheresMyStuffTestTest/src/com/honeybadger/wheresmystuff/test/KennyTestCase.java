package com.honeybadger.wheresmystuff.test;

import android.test.ActivityInstrumentationTestCase2;
import com.honeybadger.wheresmystuff.support.Item;
import com.honeybadger.wheresmystuff.support.Member;
import com.honeybadger.wheresmystuff.support.Security;
import com.honeybadger.wheresmystuff.views.LoginView;

/**
 * This is a JUnit test for the searchByName method,
 * test to see if the method can search for a name.
 * 
 * @author Kenny Wu
 *
 */
public class KennyTestCase extends ActivityInstrumentationTestCase2<LoginView> {

	private LoginView sa;
	private Item item;
	
	/**
	 * constructor for testing
	 */
	public KennyTestCase() {
		super(LoginView.class);
	}

	/**
	 * get the LoginView Activity and add a new item named test
	 */
	protected void setUp() throws Exception {
		super.setUp();
		sa = getActivity();
		item = new Item(Security.getCurrentID() ,"test" , "", new Member(Security.getCurrentMID(), "", "", ""), false, false, "", 0, 0, 0, "");
		Security.addItem(item);
	}
	
	/**
	 * This method test to see if the item is present
	 */
	public void testSearchName(){
		Security sc = new Security(sa);
		Item list = sc.getItemList().get(0);
		Boolean compare = list.getName().equals("test");
		assertTrue(compare);
	}
	
	/**
	 * This test to see if it can search for an item Name that does not exist
	 */
	public void testSearchEmptyName(){
		Security sc = new Security(sa);
		Item list = sc.getItemList().get(0);
		Boolean result = list.getName().equals(" ");
		assertTrue(!result);
	}
}
