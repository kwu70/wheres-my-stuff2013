package com.honeybadger.wheresmystuff.support;
import java.util.ArrayList;

/**
 * Member class represents a member in the system.
 */

public class Member{
	
	private String name, email, password;
	private int failedAttempts;
	private boolean lockout;
	private ArrayList<Object> items;
	private Item itemList;
	
	//Constructors
	public Member(){
		this("default@email.com", "password");
	}
	
	public Member(String email, String password){
		this(email, password, "default");
	}
	
	public Member(String email, String password, String name){
		this.email = email;
		this.password = password;
		this.name = name;
		this.lockout = false;
		this.failedAttempts = 0;
		items = new ArrayList<Object>();
		itemList = new Item();
	}
	
	/**
	 * getter for name
	 * @return name The Name of the Member
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Getter for Email
	 * @return email The Member's email
	 */
	public String getEmail(){
		return email;
	}
	
	/**
	 * Getter for password
	 * @return password The Member's password
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * Getter for Failed Attempts
	 * @return failedAttempts Number of Times the Member failed to Login
	 */
	public int getFailedAttempts(){
		return failedAttempts;
	}
	
	/**
	 * Getter for items
	 * @return items list of items
	 */
	public ArrayList<Object> getItems(){
		return items;
	}
	
	/**
	 * Setter for Name
	 * @param name String replaces old name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Setter for email
	 * @param email String replaces old email
	 */
	public void setEmail(String email){
		this.email = email;
	}
	
	/**
	 * Setter for password
	 * @param password String replaces old password
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * Setter for Failed Attempts (Login attempts)
	 * @param failedAttempts The Number that failed attempts is set to
	 */
	public void setFailedAttempts(int failedAttempts){
		this.failedAttempts = failedAttempts;
	}
	
	/**
	 * Setter for items
	 * @param items add items to list
	 */
	public void setItems(ArrayList<Object> items){
		this.items = items;
	}
	
	/**
	 * increments failedAttempts (Login Attempts) by 1
	 */
	public void incFailedAttempts(){
		this.failedAttempts++;
	}
	
	/**
	 * look for an item in the arraylist
	 */
	public void findItems(){
		items.contains(itemList);
	}
	
	/**
	 * add an item to the arraylist
	 */
	public void addItems(){
		items.add(itemList);
	}
	
	/**
	 * remove an item from the arraylist
	 */
	public void removeItems(){
		items.remove(itemList);
	}
	
}