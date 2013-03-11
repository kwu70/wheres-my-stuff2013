package com.honeybadger.wheresmystuff.support;

import java.util.ArrayList;
/**
 * The Security class contains member list which contains all the members that exist in the system.
 */
public class Security {
	
	private static ArrayList<Member> members = new ArrayList<Member>();
	private static ArrayList<Item> items = new ArrayList<Item>();
	
	/**
	 * Security constructor that sets up the ArrayLists emails, passwords, and members
	 * it initiates them with default emails and passwords
	 */
	public Security(){
		members.add(new Member("foobar@example.com", "hello"));
		members.add(new Admin("admin@admin", "admin"));
		items.add(new Item("defaultItem"));
	}
	
	/**
	 * Getter for the members list.
	 * 
	 * @return members the list that contains all the members
	 */
	public static ArrayList<Member> getMemberList(){
		return members;
	}
	
	/**
	 * Getter for item list
	 * 
	 * @return items the list that contains all the items (general list of items)
	 */
	public static ArrayList<Item> getItemList(){
		return items;
	}
	
	/**
	 * Getter for member list Size
	 *  
	 * @return members.size() an int indicating the size of the list.
	 */
	public static int getMemberListSize(){
		return members.size();
	}
	
	/**
	 * Getter for item size
	 * 
	 * @return items.size() an int indicating the size of the list.
	 */
	public static int getItemListSize(){
		return items.size();
	}
	
	
	/**
	 * Searches through the list of all members for the member with the specified email
	 * 
	 * @param email Email of the specific member
	 * @return Member with the email. If the email is not found then return default Member.
	 */
	public static Member getMember(String email){
		for(int i = 0; i < members.size(); i++){
			if(email.equals(members.get(i).getEmail())){
				return members.get(i);
			}
		}
		return new Member("default","default");
	}
	
	/**
	 * Determines if a member is in the list based on the email
	 * 
	 * @param email Email of specific member
	 * @return boolean based on whether the Member is in the list.
	 */
	public static boolean contains(String email){
		for(int i = 0; i < members.size(); i++){
			if(email.equals(members.get(i).getEmail())){
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
		members.add(new Member(email, password));
	}
	
	/**
	 * Adds a new Item to the item list
	 *
	 * @param item the Item being added to the item list
	 */
	public static void addItem(Item item){
		items.add(item);
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
		members.add(new Admin(email, password));
	}
	
	/**
	 * Remove a Member from the member list based on email.
	 * 
	 * @param email
	 * @param password
	 */
	public static boolean removeMember(String email){
		for(int i = 0; i < members.size(); i++){
			if(email.equals(members.get(i).getEmail())){
				members.remove(i);
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
		member.setFailedAttempts(0);
	}
	
}
