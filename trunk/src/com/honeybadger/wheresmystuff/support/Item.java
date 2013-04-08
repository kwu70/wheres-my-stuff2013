package com.honeybadger.wheresmystuff.support;

/**
 * Item class represents an Item created by a Member
 * 
 * @author Honey Badger
 */

public class Item {
	
	private String name, description, type;
	
	private final String location;
	
	private static final String[] CATEGORY = new String[]
			{"Food", "Clothing", "Personal", "Donation"};
	
	private boolean status, resolved;
	
	private Member owner;
	
	private final int month, day, year, id;
		
	/**
	 * Third advanced constructor for Item. 
	 * Includes: name, description, owner, status, resolved, and type
	 * 
	 * @param id id of the item in the databas
	 * @param name The name of the item
	 * @param description Description of the item
	 * @param owner Member that owns the item
	 * @param status Whether the item is listed as Lost or Found
	 * @param resolved Whether the item has been claimed or not
	 * @param type The category the item is listed as
	 * @param month Month that the item was entered
	 * @param day Day that the item was entered
	 * @param year Year that the item was entered
	 * @param location String showing location of item
	 */
	public Item(int id, String name, String description, Member owner, boolean status, 
			boolean resolved, String type, int month, int day, int year, String location){
		this.id = id;
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.status = status;
		this.resolved = resolved;
		this.type = type;
		this.month = month;
		this.day = day;
		this.year = year;
		this.location = location;
	}

	/**
	 * Getter for the item name.
	 * 
	 * @return name Represents the Items name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Setter for the item name.
	 * 
	 * @param name Represents the Item's name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Getter for the Description.
	 * 
	 * @return description Represents the Items' description
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Setter for the Description.
	 * 
	 * @param description Represents the Item's description
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Getter for the owner.
	 * 
	 * @return Owner Represents the owner of the Item
	 */
	public Member getOwner(){
		return owner;
	}
	
	/**
	 * Setter for the owner.
	 * 
	 * @param owner Represents the owner of the Item
	 */
	public void setOwner(Member owner){
		this.owner = owner;
	}
	
	/**
	 * Getter for the item status.
	 * 
	 * @return status Represents the Item's status
	 */
	public boolean getStatus(){
		return status;
	}
	
	/**
	 * Setter for the item status.
	 * 
	 * @param status Represents the Item's status
	 */
	public void setStatus(boolean status){
		this.status = status;
	}
	
	/**
	 * Getter for resolved.
	 * 
	 * @return resolved Represents resvolved
	 */
	public boolean getResolved(){
		return resolved;
	}
	
	/**
	 * Sets resolved.
	 * 
	 * @param resolved Represents resolved
	 */
	public void setResolved(boolean resolved){
		this.resolved = resolved;
	}
	
	/**
	 * Getter for the item type.
	 * 
	 * @return type Represents the Item's type
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * Getter for month
	 * 
	 * @return month Represents month the Item was entered
	 */
	public int getMonth(){
		return month;
	}
	
	/**
	 * Getter for day
	 * 
	 * @return day Represents day the Item was entered
	 */
	public int getDay(){
		return day;
	}
	
	/**
	 * Getter for year
	 * 
	 * @return year Represents year the Item was entered
	 */
	public int getYear(){
		return year;
	}
	
	/**
	 * Getter for location
	 * 
	 * @return location Represents location the Item is at
	 */
	public String getLocation(){
		return location;
	}
	
	/**
	 * Setter for the item type.
	 * 
	 * @param type Represents type of the Item
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * Getter for the list of categories
	 * 
	 * @return category Represents the Item's category
	 */
	public static String[] getListOfCategories(){
		return CATEGORY;
	}
	
	/**
	 * Getter for the size of the category list
	 * 
	 * @return category.length Represents the length the List
	 */
	public int sizeOfCategoryList(){
		return CATEGORY.length;
	}
	
	/**
	 * Getter for the item's ID
	 * 
	 * @return id Represents Item's id in database
	 */
	public int getID(){
		return id;
	}
}