package com.honeybadger.wheresmystuff.support;

import java.util.ArrayList;
/**
 * The Security class contains member list which contains all the members that exist in the system.
 */
public class Security {
	
	private static ArrayList<Member> members = new ArrayList<Member>();
	
	/**
	 * Security constructor that sets up the ArrayLists emails, passwords, and members
	 * it initiates them with default emails and passwords
	 */
	public Security(){
		members.add(new Member("example@example.com", "example"));
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
	 * Getter for member list Size
	 *  
	 * @return members.size() an int indicating the size of the list.
	 */
	public static int getMemberListSize(){
		return members.size();
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
		return new Member();
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
	
}
