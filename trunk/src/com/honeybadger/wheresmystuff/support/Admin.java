package com.honeybadger.wheresmystuff.support;

import android.os.Bundle;

/*
 * This class is used to create Admins and for Views to access admin only methods
 */
public class Admin extends Member {

	/*
	 * This constructor calls the super constructor to make a new admin
	 * 
	 * @param email - email of new Admin
	 * @param password - password of new Admin
	 */
	public Admin(String email, String password) {
		super(email, password);
	}
	
	/*
	 * This constructor is a more detailed constructor and allows their 
	 * to be a username for the admin
	 * 
	 * @param email - email of new Admin
	 * @param password - password of new Admin
	 * @param name - name of new Admin
	 */
	public Admin(String email, String password, String name){
		super(email, password, name);
	}
	
	/*
	 * This method removes a member from the member list so that they may no
	 * longer login
	 * 
	 * @param mem - member instance of the member to be deleted
	 */
	public void removeMember(Member mem){
		Security.removeMember(mem.getEmail());
	}
	
	/*
	 * This method creates a new admin and adds them to the
	 * member list so they can login as an admin
	 * 
	 * @param email - email of new Admin
	 * @param password - password of new Admin
	 */
	public void createAdmin(String email, String password){
		Security.addAdmin(email, password);
	}
	
	
	/*
	 * This method is used to unlock an account
	 * 
	 * @param mem- member that needs his/her account unlocked
	 */
	public void unlockAccount(Member mem){
		Security.resetAttempts(mem);
	}
}
