package com.honeybadger.wheresmystuff.test;

import java.util.List;

import com.honeybadger.wheresmystuff.support.Item;
import com.honeybadger.wheresmystuff.support.Member;
import com.honeybadger.wheresmystuff.support.Search;
import com.honeybadger.wheresmystuff.support.Security;
import com.honeybadger.wheresmystuff.views.LoginView;

/**
* JUnit Test for the filterDate Method in Search.
* The method filter's a member's items to show only those 
* added on or before a specified date.
* 
* @author HoneyBadger - Jayne
* @version 1.0
*/
public class JayneTestCase extends android.test.ActivityInstrumentationTestCase2<LoginView>{
	
	//new instance of LoginView
	private LoginView lv;
	
	//new member to add in order to test the method
	private Member member;
	
	//instances of Item to use in the test
	private Item item1, item2, item3, item4;
	
	/**
	 * Constructor required for testing.
	 */
	public JayneTestCase(){
		super(LoginView.class);
	}
	
	/**
	* Adds a member with email "example@example.com" and password "test"
	*/
	@Override
	public void setUp(){
		lv = getActivity();
		member = new Member(Security.getCurrentID(), "example@example.com", "test", "Name");
		Security.addMember(member.getEmail(), member.getPassword());
	}
	
	/**
	* Tests the filterDate method by comparing the filtered list
	* with null date to an empty list.
	*/
	public void testFilterDate1(){
		final List<Item> list = Search.filterDate(member, null);
		Boolean result = false;
		
		if(list.size() == 0){
			result = true;
		}
		
		assertTrue(result);
	}
	
	/**
	* Tests the filterDate method by comparing a filtered list
	* with an empty string inputted for the date to an empty list
	*/
	public void testfilterDate2(){
		final List<Item> list = Search.filterDate(member, "");
		Boolean result = false;
		
		if(list.size() == 0){
			result = true;
		}
		
		assertTrue(result);
	}
	
	/**
	*Tests the filterDate method by comparing a filtered list
	* of items added on or after "04/01/2013"
	*/
	public void testfilterDate3(){
		item1 = new Item(Security.getCurrentID(), "item1", "test on date", member,
						false, false, "personal", 4, 10, 2013, "Atlanta");
		item2 = new Item(Security.getCurrentID(), "item2", "test on date", member,
						false, false, "personal", 4, 10, 2013, "Atlanta");
		item3 = new Item(Security.getCurrentID(), "item3", "test before date", member,
						false, false, "personal", 3, 31, 2013, "Atlanta");
		item4 = new Item(Security.getCurrentID(), "item4", "test after date", member,
						false, false, "personal", 5, 2, 2013, "Atlanta");
		
		Security.addItem(item1);
		Security.addItem(item2);
		Security.addItem(item3);
		Security.addItem(item4);
		
		final List<Item> filteredItems = Search.filterDate(member, "04/10/2013");
	
		Boolean result = false;
	
		if(filteredItems.size() == 0){
			result = false;
		}
		for(int i = 0; i < filteredItems.size(); i++){
			if(filteredItems.get(i).getMonth() > 4
					&& filteredItems.get(i).getYear() >= 2013){
				result = true;
			}else if(filteredItems.get(i).getMonth() == 4 
					&& filteredItems.get(i).getDay() >= 10
					&& filteredItems.get(i).getYear() == 2013){
				result = true;
			}
		}
		assertTrue(result);
	}
}
	
	
