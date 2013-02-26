package com.honeybadger.wheresmystuff.support;

/**
 * The Login class is responsible for verifying login credentials, logging
 * a user in if they exist, locking a user out if they are incorrect, and 
 * creating an account if the email does not yet exist.
 */
public class Login {
	private Security sc;
	
	/**
	 * Login constructor that creates a new Security object.
	 */
	public Login(){
		sc = new Security();
	}
	
	/**
	 * Prevents user from logging in.
	 *
	 * @return 		true if user is locked out, false otherwise
	 */
	public boolean lockOut(){
		return false;
	}
	
	/**
	 * Checks if member is part of the system using the email inputted by the user.
	 * If email and password for the email are correct then it will return true
	 * Otherwise it returns false
	 *
	 * @param email User's inputted email address
	 * @param psswd User's inputted password
	 * @return 		whether combination of login credentials exists or not 
	 */
	public boolean validate(String email, String password){
		int index = 0;
		for(int i = 0; i < Security.members.size(); i++){
			if(email.equals(Security.members.get(i).getEmail())){
				index = i;
			}
		}
		if(password.equals(Security.members.get(index).getPassword())){
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
	public boolean createAccount(String email, String psswd) {
		sc.emails.add(email);
		sc.passwords.add(psswd);
		Security.members.add(new Member(email, psswd));
		return true;
	}
}
