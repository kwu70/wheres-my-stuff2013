package com.honeybadger.wheresmystuff.support;

/**
 * Item class represents and Item created by a Member
 */

public class Item {
	
	private String name, description;
	private boolean status;
	private Member owner;
	
	/**
	 * Default Constructor for Item
	 */
	public Item(){
		
	}
	
	/**
	 * Basic Constructor for Item includes only a String name
	 * @param name The name of the Item
	 */
	public Item(String name){
		this.name = name;
	}
	
	/**
	 * Advanced Constructor for Item includes name, description, owner, and status
	 * 
	 * @param name The Name of the Item
	 * @param description Description of the Item 
	 * @param owner Who is the Member that owns this Item
	 * @param status Whether the Item is lost or found
	 */
	public Item(String name, String description, Member owner, boolean status){
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.status = status;
	}
	
	
	//Getters/Setters
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
}