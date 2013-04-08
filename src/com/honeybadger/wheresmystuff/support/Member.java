package com.honeybadger.wheresmystuff.support;
import java.util.ArrayList;

/**
 * Member class represents a member in the system.
 * @author - TheHoneyBadgers
 * @version - 1
 */

public class Member{
	
	//name email and password of member
	private final String name, email;

	private final String password;
	
	//number of failed logins
	private int failedAttempts;
	
	//ID of member inside database
	private final int id;
	
	/**
	 * 
	 * @param id - id of member in database
	 * @param email - email of member
	 * @param password - password of member
	 * @param name - name of member
	 */
	public Member(int id, String email, String password, String name){
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.failedAttempts = 0;
	}
	
	/**
	 * getter for name of the member
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
	public ArrayList<Item> getItems(){
		final DatabaseHandlerItems db = Security.getDBI();
		final ArrayList<Item> item = db.getAllItems();
		final ArrayList<Item> temp = item;
		temp.clear();
		for(Item i: item){
			if(i.getOwner().equals(this)){
				temp.add(i);
			}
		}
		return temp;
	}
	
	/**
	 * Setter for Failed Attempts (Login attempts)
	 * @param failedAttempts The Number that failed attempts is set to
	 */
	public void setFailedAttempts(int failedAttempts){
		this.failedAttempts = failedAttempts;
		Security.updateMember(this);
	}
	
	/**
	 * increments failedAttempts (Login Attempts) by 1
	 */
	public void incFailedAttempts(){
		Member temp = Security.getMember(this.email);
		temp.failedAttempts++;
		Security.updateMember(temp);
	}
	
	/**
	 * 
	 * @return id of member in database
	 */
	public int getID(){
		return id;
	}
	
}