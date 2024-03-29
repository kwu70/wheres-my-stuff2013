package com.honeybadger.wheresmystuff.support;

import java.util.ArrayList;

import android.content.Context;

/**
 * The Security class contains the list of members.
 * which contains all the members that exist in the system.
 * 
 * @author Honey Badger
 * @version 1.0
 */
public class Security {

	/**
 	* Static variable for handling database Member class 
 	*/
	private static DatabaseHandlerMembers dbHandlerM;
	
	/**
 	* Static variable for handling database Item class 
 	*/
	private static DatabaseHandlerItems dbHandlerI;
	
	/**
	 * Security constructor that sets up the ArrayLists emails, passwords, and members.
	 * It initiates them with default emails and passwords
	 * 
	 * @param c 
	 */
	public Security(Context c){
		Security.dbHandlerM = new DatabaseHandlerMembers(c);
		Security.dbHandlerI = new DatabaseHandlerItems(c);
		if(dbHandlerM.getCurrentMemberID() == 1){
			dbHandlerM.addMember(new Member(dbHandlerM.getCurrentMemberID(), "foobar@example.com", "hello", ""));
			dbHandlerM.addMember(new Admin(dbHandlerM.getCurrentMemberID(), "admin@admin", "admin", ""));
		}
	}

	/**
	 * Getter for the members list.
	 * 
	 * @return members the list that contains all the members
	 */
	public static ArrayList<Member> getMemberList(){
		return dbHandlerM.getAllMembers();
	}

	/**
 	* Getter for the item list
 	* 
 	* @return items the list that contains all the items (general list of items)
 	*/
	public static ArrayList<Item> getItemList(){
		return dbHandlerI.getAllItems();
	}
	
	/**
	 * Getter for member list Size
	 *  
	 * @return members.size() an int indicating the size of the list.
	 */
	public static int getMemberListSize(){
		return dbHandlerM.getAllMembers().size() - 1;
	}
	
	/**
	 * Getter for item size
	 * 
	 * @return items.size() an int indicating the size of the list.
	 */
	public static int getItemListSize(){
		return dbHandlerI.getAllItems().size() - 1;
	}
	
	
	/**
	 * Searches through the list of all members for
	 * the member with the specified email.
	 * 
	 * @param email Email of the specific member
	 * @return Member email, if not found return default Member.
	 */
	public static Member getMember(String email){
		ArrayList<Member> mem = dbHandlerM.getAllMembers();
		for(int i = 0; i < mem.size(); i++){
			if(email.equals(mem.get(i).getEmail())){
				return mem.get(i);
			}
		}
		return new Member(0,"default","default","");
	}
	
	/**
	 * Gets the failed attempts of the user
	 * @param mem - member to get the failed attempts for
	 * @return number of failed attempts
	 */
	public static int getFailedAttemps(Member mem){
		return dbHandlerM.getMember(mem.getID()).getFailedAttempts();
	}
	
	/**
	 * Updates all the members values
	 * @param mem - member to be updated
	 */
	public static void updateMember(Member mem){
		dbHandlerM.updateMember(mem);
	}
	
	/**
	 * Determines if a member is in the list based on email
	 * 
	 * @param email Email of specific member
	 * @return boolean based on whether the Member is in the list.
	 */
	public static boolean contains(String email){
		ArrayList<Member> mem = dbHandlerM.getAllMembers();
		for(int i = 0; i < mem.size(); i++){
			if(email.equals(mem.get(i).getEmail())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a new Member to the member list. 
	 * 
	 * NOTE: MAKE SURE THAT THE EMAIL DOES NOT ALREADY EXIST BEFORE CALLING THIS METHOD
	 * YOU CAN DO THIS USING CONTAINS METHOD
	 * 
	 * @param email of the Member being added to the member list
	 * @param password  of the Member being added to the member list
	 */
	public static void addMember(String email, String password){
		dbHandlerM.addMember(new Member(dbHandlerM.getCurrentMemberID(), email,password, ""));
	}
	
	/**
	 * Adds a new Item to the item list
	 *
	 * @param item the Item being added to the item list
	 */
	public static void addItem(Item item){
		if(item != null){
			dbHandlerI.addItem(item);
		}		
	}
	
	/**
	 * Adds a new Admin to the member list. 
	 * 
	 * NOTE: MAKE SURE THAT THE EMAIL DOES NOT ALREADY EXIST BEFORE CALLING THIS METHOD
	 * YOU CAN DO THIS USING CONTAINS METHOD
	 * 
	 * @param email of the Admin being added to the member list
	 * @param password  of the Admin being added to the member list
	 */
	public static void addAdmin(String email, String password){
		dbHandlerM.addMember(new Admin(dbHandlerM.getCurrentMemberID(), email, password, "Admin"));
	}
	
	/**
	 * Remove a Member from the member list based on email.
	 * 
	 * @param email
	 * @return true when email match member to be removed, else false
	 */
	public static boolean removeMember(String email){
		ArrayList<Member> mem = dbHandlerM.getAllMembers();
		for(int i = 0; i < mem.size(); i++){
			if(email.equals(mem.get(i).getEmail())){
				dbHandlerM.deleteMember(mem.get(i));
				return true;
			}
		}
		return false;
	}

	/**
	 * Resets the failed attempts of specified member 
	 * 
	 * @param member the member that is having their failed attempts reset to zero.
	 */
	public static void resetAttempts(Member member) {
		if(member != null){
			dbHandlerM.getMember(member.getID()).setFailedAttempts(0);
		}
	}
	
	/**
	 * Resets FailedAttempts to zero.
	 * 
	 * @param email Email of the memebr that is being unlocked.
	 */
	public static void unlockMember(String email){
		ArrayList<Member> mem = dbHandlerM.getAllMembers();
		for(int i = 0; i < mem.size(); i++){
			if(email.equals(mem.get(i).getEmail())){
				mem.get(i).setFailedAttempts(0);
				Security.updateMember(mem.get(i));
			}
		}
	}
	
	/**
	 * Getter for ItemList
	 * 
	 * @param mem
	 * @return temp
	 */
	public static ArrayList<Item> getMemberItemList(Member mem){
		ArrayList<Item> items = dbHandlerI.getAllItems();
		ArrayList<Item> temp = dbHandlerI.getAllItems();
		temp.clear();
		for(Item i: items){
			if(i.getOwner() != null){
				if(i.getOwner().getID() == mem.getID()){
					temp.add(i);
				}
			}
		}
		return temp;
	}
	
	/**
	 * Getter for currentItemID
	 * 
	 * @return dbHandlerI.getCurrentItemID()
	 */
	public static int getCurrentID(){
		return dbHandlerI.getCurrentItemID();
	}
	
	/**
	 * Getter for currentMemberID
	 * 
	 * @return dbHandlerI.getCurrentMemberID();
	 */
	public static int getCurrentMID(){
		return dbHandlerM.getCurrentMemberID();
	}
	
	/**
	 * Getter from DBM
	 * 
	 * @return dbHandlerM
	 */
	public static DatabaseHandlerMembers getDBM(){
		return dbHandlerM;
	}
	
	/**
	 * Getter for DBI
	 * 
	 * @return dbHandlerI
	 */
	public static DatabaseHandlerItems getDBI(){
		return dbHandlerI;
	}
	
	/**
	 * toString() for Security class
	 * 
	 * @return null returns nothing
	 */
	public String toString() {
		return null;
	}
}
