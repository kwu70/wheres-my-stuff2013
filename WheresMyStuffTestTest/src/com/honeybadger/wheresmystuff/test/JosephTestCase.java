package com.honeybadger.wheresmystuff.test;

import java.util.ArrayList;
import java.util.Calendar;

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
		Security.addMember("test@test.com", "test");
		member = Security.getMember("test@test.com");
	}

	/**
	 * Tests the filterCategory method by comparing a filtered list with
	 * null category to an empty list
	 */
	public void testfilterCategory1(){

		ArrayList<Item> list = Search.filterCategory(member, null);
		ArrayList<Item> empty = new ArrayList<Item>();
		Boolean result = false;

		if(list == empty){
			result = true;
		}

		assertTrue(result);
	}

	/**
	 * Tests the filterCategory method by comparing a filtered list with
	 * empty string category to an empty list
	 */
	public void testfilterCategory2(){

		ArrayList<Item> list = Search.filterCategory(member, "");
		ArrayList<Item> empty = new ArrayList<Item>();
		Boolean result = false;

		if(list == empty){
			result = true;
		}

		assertTrue(result);
	}

	/**
	 * Tests the filterCategory method by comparing a filtered list with
	 * food category to a list showing just food items
	 */
	public void testfilterCategory3(){

		Security.addItem(new Item(1, "shirt", "black", member, 
				false, false, "clothing", 02, 06, 2013, "Atlanta"));

		Security.addItem(new Item(2, "apple", "red", member, 
				false, false, "food", 02, 06, 2013, "Atlanta"));

		Security.addItem(new Item(3, "pants", "blue", member, 
				false, false, "clothing", 02, 06, 2013, "Atlanta"));

		Security.addItem(new Item(4, "banana", "yellow", member, 
				false, false, "food", 02, 06, 2013, "Atlanta"));

		ArrayList<Item> fullList = Security.getMemberItemList(member);
		fullList.remove(0);
		fullList.remove(2);

		ArrayList<Item> filterList = Search.filterCategory(member, "food");

		Boolean result = true;

		for(int i = 0; i < filterList.size(); i++){
			Item currFullItem = fullList.get(i);
			Item currFilterItem = filterList.get(i);
			if(!currFullItem.getType().equals(currFilterItem.getType())){
				assertTrue(!result);
			}
		}

		assertTrue(result);
	}

}