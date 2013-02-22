package com.honeybadger.wheresmystuff.support;

import java.util.ArrayList;

/**
 * The Security class contains email and password lists with which user
 * input can be checked.
 */
public class Security {
	protected ArrayList<String> emails = new ArrayList<String>();
	protected ArrayList<String> passwords = new ArrayList<String>();
	
	/**
	 * Security constructor that sets up the ArrayLists emails and passwords
	 */
	public Security(){
		emails.add("foo@example.com");
		passwords.add("hello");
	}
	
	
	
}
