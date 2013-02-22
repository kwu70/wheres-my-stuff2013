package com.honeybadger.wheresmystuff;

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
	 * Checks if email string is found in the Security object's email list and
	 * if not, a new account is created.
	 * If password is not found in password list, false is returned.
	 *
	 * @param email User's inputted email address
	 * @param psswd User's inputted password
	 * @return 		whether combination of login credentials exists or not 
	 */
	public boolean validate(String email, String psswd){
		int emailIndex = 0;
		for(int i =0; i < sc.emails.size(); i++){
			if(email.equals(sc.emails.get(i))){
				emailIndex = i;
			}
			else{
				return createAccount(email, psswd);
			}
		}
		if(psswd.equals(sc.passwords.get(emailIndex))){
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
	private boolean createAccount(String email, String psswd) {
		sc.emails.add(email);
		sc.passwords.add(psswd);
		return true;
	}
}
