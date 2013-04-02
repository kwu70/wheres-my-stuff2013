package com.honeybadger.wheresmystuff.support;

/**
 * This class is used to create Admins and for Views to access admin only methods
 * 
 *  @author Honey Badger
 */
public class Admin extends Member {

	
	/**
	 * This constructor is a more detailed constructor and allows their 
	 * to be a username for the admin
	 * 
	 * @param id - each member has id in database
	 * @param email - email of new Admin
	 * @param password - password of new Admin
	 * @param name - name of new Admin
	 */
	public Admin(int id, String email, String password, String name){
		super(id, email, password, name);
	}
	
	/**
	 * This method removes a member from the member list so that they may no
	 * longer login
	 * 
	 * @param mem - member instance of the member to be deleted
	 */
	public void removeMember(Member mem){
		Security.removeMember(mem.getEmail());
	}
	
	/**
	 * This method creates a new admin and adds them to the
	 * member list so they can login as an admin
	 * 
	 * @param email - email of new Admin
	 * @param password - password of new Admin
	 */
	public void createAdmin(String email, String password){
		Security.addAdmin(email, password);
	}
	
	/**
	 * This method is used to unlock an account
	 * 
	 * @param mem Member that needs his/her account unlocked
	 */
	public void unlockAccount(Member mem){
		Security.resetAttempts(mem);
	}
}
