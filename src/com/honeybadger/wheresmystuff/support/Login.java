package com.honeybadger.wheresmystuff.support;

/**
 * The Login class is responsible for verifying login credentials, logging
 * a user in if they exist, locking a user out if they enter incorrect information
 * three times, and creating an account if the email does not yet exist.
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
	 * Prevents user from logging in if login attempts equal to 3.
	 * 
	 * @param email Email of the specific member.
	 * @return boolean True if user is locked out, false otherwise.
	 */
	public boolean lockOut(Member member){
		return member.getFailedAttempts() == 3;
	}

	/**
	 * Checks if member is part of the system using the email inputed by the user.
	 * If email and password for the email are correct, then it will return true.
	 * Otherwise, it returns false.
	 *
	 * @param email User's inputed email address.
	 * @param psswd User's inputed password.
	 * @return boolean Whether combination of login credentials exists or not. 
	 */
	public boolean validate(String email, String password){	
		if(Security.getMemberList().contains(email)){
			Member temp = Security.getMember(email);
			if(password.equals(temp.getPassword())
						&& !lockOut(temp)){
				temp.setFailedAttempts(0);
				return true;
			}
			else{
				temp.incFailedAttempts();
				return false;
			}
		}
		// Else, email string is available and user can be prompted to register
		// with new email.
		return false;
	}


}
