package com.honeybadger.wheresmystuff.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.honeybadger.wheresmystuff.support.Item;
import com.honeybadger.wheresmystuff.support.Login;
import com.honeybadger.wheresmystuff.support.Member;
import com.honeybadger.wheresmystuff.support.Search;
import com.honeybadger.wheresmystuff.support.Security;
import com.honeybadger.wheresmystuff.views.LoginView;

/**
 * JUnit Test for filterCategory Method in Search
 * Filters member's items to show only those that have a given category.
 * 
 * @author Joseph Alexander
 */
public class JosephTestCase extends android.test.ActivityInstrumentationTestCase2<LoginView> {

	private LoginView lv;
	private Member member;
	private Item i1, i2, i3, i4;

	//required constructor for Testing
	public JosephTestCase(){
		super(LoginView.class);
	}

	/**
	 * Adds a member with the email test@test.com and password test
	 */
	@Override
	public void setUp(){
		lv = getActivity();
		member = new Member(Security.getCurrentID(), "test@test.com", "test", "Name");
		Security.addMember(member.getEmail(),member.getPassword());
	}

	/**
	 * Tests the filterCategory method by comparing a filtered list with
	 * null category to an empty list
	 */
	public void testfilterCategory1(){

		List<Item> list = Search.filterCategory(member, null);
		Boolean result = false;

		if(list.size() == 0){
			result = true;
		}

		assertTrue(result);
	}

	/**
	 * Tests the filterCategory method by comparing a filtered list with
	 * empty string category to an empty list
	 */
	public void testfilterCategory2(){

		ArrayList<Item> list = (ArrayList<Item>) Search.filterCategory(member, "");
		Boolean result = false;

		if(list.size() == 0){
			result = true;
		}

		assertTrue(result);
	}

	/**
	 * Tests the filterCategory method by comparing a filtered list with
	 * food category to a list showing just food items
	 */
	public void testfilterCategory3(){
		i1 = new Item(Security.getCurrentID(), "shirt", "black", member, 
				false, false, "clothing", 02, 06, 2013, "Atlanta");
		i2 = new Item(Security.getCurrentID(), "apple", "red", member, 
				false, false, "Food", 02, 06, 2013, "Atlanta");
		i3 = new Item(Security.getCurrentID(), "pants", "blue", member, 
				false, false, "clothing", 02, 06, 2013, "Atlanta");
		i4 = new Item(Security.getCurrentID(), "banana", "yellow", member, 
				false, false, "Food", 02, 06, 2013, "Atlanta");
		
		Security.addItem(i1);
		Security.addItem(i2);
		Security.addItem(i3);
		Security.addItem(i4);
		
		List<Item> filterList = Search.filterCategory(member, "Food");

		Boolean result = false;

		if((filterList.size() != 0) && (filterList.get(0).getID() == i2.getID()) && (filterList.get(1).getID() == i4.getID())){
			result = true;
		}

		assertTrue(result);
	}
}