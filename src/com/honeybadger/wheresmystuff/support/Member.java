package com.honeybadger.wheresmystuff.support;

/**
 * Member class represents a member in the system.
 */

public class Member{
	
	private String name, email, password;
	private int failedAttempts;
	private boolean lockout;
	
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
	 * increments failedAttempts (Login Attempts) by 1
	 */
	public void incFailedAttempts(){
		this.failedAttempts++;
	}
	
}