package com.honeybadger.wheresmystuff.support;

import java.util.ArrayList;

import android.content.Context;
/**
 * The Security class contains member list which contains all the members that exist in the system.
 */
public class Security {
	
	//private static ArrayList<Member> members = new ArrayList<Member>();
	//private static ArrayList<Item> items = new ArrayList<Item>();
	private static DatabaseHandler dbHandler;
	
	/**
	 * Security constructor that sets up the ArrayLists emails, passwords, and members
	 * it initiates them with default emails and passwords
	 */
	public Security(Context c){
		Security.dbHandler = new DatabaseHandler(c);
		dbHandler.addMember(new Member(dbHandler.getCurrentMemberID(), "foobar@example.com", "hello", ""));
		dbHandler.addMember(new Admin(dbHandler.getCurrentMemberID(), "admin@admin", "admin", ""));
	}
	
	/**
	 * Getter for the members list.
	 * 
	 * @return members the list that contains all the members
	 */
	public static ArrayList<Member> getMemberList(){
		return dbHandler.getAllMembers();
	}
	
	/**
	 * Getter for item list
	 * 
	 * @return items the list that contains all the items (general list of items)
	 */
	public static ArrayList<Item> getItemList(){
		return dbHandler.getAllItems();
	}
	
	/**
	 * Getter for member list Size
	 *  
	 * @return members.size() an int indicating the size of the list.
	 */
	public static int getMemberListSize(){
		return dbHandler.getAllMembers().size();
	}
	
	/**
	 * Getter for item size
	 * 
	 * @return items.size() an int indicating the size of the list.
	 */
	public static int getItemListSize(){
		return dbHandler.getAllItems().size();
	}
	
	
	/**
	 * Searches through the list of all members for the member with the specified email
	 * 
	 * @param email Email of the specific member
	 * @return Member with the email. If the email is not found then return default Member.
	 */
	public static Member getMember(String email, Context c){
		Security.dbHandler = new DatabaseHandler(c);
		ArrayList<Member> mem = dbHandler.getAllMembers();
		for(int i = 0; i < mem.size(); i++){
			if(email.equals(mem.get(i).getEmail())){
				return mem.get(i);
			}
		}
		return new Member(0,"default","default","");
	}
	
	/**
	 * Determines if a member is in the list based on the email
	 * 
	 * @param email Email of specific member
	 * @return boolean based on whether the Member is in the list.
	 */
	public static boolean contains(String email){
		ArrayList<Member> mem = dbHandler.getAllMembers();
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
		dbHandler.addMember(new Member(dbHandler.getCurrentMemberID(), email,password, ""));
	}
	
	/**
	 * Adds a new Item to the item list
	 *
	 * @param item the Item being added to the item list
	 */
	public static void addItem(Item item){
		dbHandler.addItem(item);
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
		dbHandler.addMember(new Admin(dbHandler.getCurrentMemberID(), email, password, ""));
	}
	
	/**
	 * Remove a Member from the member list based on email.
	 * 
	 * @param email
	 * @param password
	 */
	public static boolean removeMember(String email){
		ArrayList<Member> mem = dbHandler.getAllMembers();
		for(int i = 0; i < mem.size(); i++){
			if(email.equals(mem.get(i).getEmail())){
				mem.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * resets failed attempts of specified member 
	 * 
	 * @param member the member that is having their failed attempts reset to zero.
	 */
	public static void resetAttempts(Member member) {
		dbHandler.getMember(member.getID()).setFailedAttempts(0);
	}
	
	/**
	 * Resets failed attempts to zero.
	 * 
	 * @param email Email of the memebr that is being unlocked.
	 */
	public static void unlockMember(String email){
		ArrayList<Member> mem = dbHandler.getAllMembers();
		for(int i = 0; i < mem.size(); i++){
			if(email.equals(mem.get(i).getEmail())){
				mem.get(i).setFailedAttempts(0);
			}
		}
	}
	
	public static DatabaseHandler getDB(){
		return dbHandler;
	}
	
}
