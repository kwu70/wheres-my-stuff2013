package com.honeybadger.wheresmystuff.support;


/**
 * The Login class is responsible for verifying login credentials, logging
 * a user in if they exist, locking a user out if they are incorrect, and 
 * creating an account if the email does not yet exist.
 */
public class Login {
	
	/*
	 * Used to set up initial usernames and passcodes
	 */
	private static int timesAccessed;
	/**
	 * Login constructor that creates a new Security object.
	 */
	public Login(){
	}
	
	/**
	 * Prevents user from logging in.
	 *
	 * @return 		true if user is locked out, false otherwise
	 */
	public static boolean lockOut(){
		return false;
	}
	
	/**
	 * Checks if email string is found in the Security object's email list and
	 * if not, a new account is created.
	 * If password is not found in password list, false is returned.
	 *
	 * @param email User's inputted email address
	 * @param psswd User's inputted password
	 * @return 		whether combination of login credentials exists or not 
	 */
	public static boolean validate(String email, String psswd){
		if(timesAccessed == 0){
			Security.emails.add("foo@example.com");
			Security.passwords.add("hello");
			timesAccessed++;
		}
		int emailIndex = 0;
		for(int i =0; i < Security.emails.size(); i++){
			if(email.equals(Security.emails.get(i))){
				emailIndex = i;
			}
			else{
				return false;
			}
		}
		if(psswd.equals(Security.passwords.get(emailIndex))){
			return true;
		}
		return false;
	}
	
	/**
	 * Adds user credentials to email and password ArrayLists.
	 *
	 * @param email User's inputted email address
	 * @param psswd User's inputted password
	 * @return 		true boolean value
	 */
	public static boolean createAccount(String email, String psswd) {
		Security.emails.add(email);
		Security.passwords.add(psswd);
		return true;
	}
}
