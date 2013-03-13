package com.honeybadger.wheresmystuff.support;

/**
 * Item class represents and Item created by a Member
 */

public class Item {
	
	private String name, description, type;
	private static String[] category = new String[]{"Food", "Clothing", "Personal","Donation"};
	private boolean status, resolved;
	private Member owner;
	private int month, day, year;
		
	/**
	 * Basic Constructor for Item includes only a String name
	 * @param name The name of the Item
	 */
	public Item(String name){
		this(name, "", null, false);
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
		this(name, description, owner, status, false);
	}
	/**
	 * Second advanced constructor for Item. Includes: name, description, owner, status, resolved, and type
	 * 
	 * @param name The name of the item
	 * @param description Description of the item
	 * @param owner Member that owns the item
	 * @param status Whether the item is listed as Lost or Found
	 * @param resolved Whether the item has been claimed or is still in circulation in the app
	 */
	public Item(String name, String description, Member owner, boolean status, boolean resolved){
		this(name, description, owner, status, resolved, null, 0, 0, 0);
	}
	/**
	 * Third advanced constructor for Item. Includes: name, description, owner, status, resolved, and type
	 * 
	 * @param name The name of the item
	 * @param description Description of the item
	 * @param owner Member that owns the item
	 * @param status Whether the item is listed as Lost or Found
	 * @param resolved Whether the item has been claimed or is still in circulation in the app
	 * @param type The category the item is listed as
	 */
	public Item(String name, String description, Member owner, boolean status, boolean resolved, String type, int month, int day, int year){
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.status = status;
		this.resolved = resolved;
		this.type = type;
		this.month = month;
		this.day = day;
		this.year = year;
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
	
	public Member getOwner(){
		return owner;
	}
	
	public void setOwner(Member owner){
		this.owner = owner;
	}
	
	public boolean getStatus(){
		return status;
	}
	
	public void setStatus(boolean status){
		this.status = status;
	}
	
	public boolean getResolved(boolean resolved){
		return resolved;
	}
	
	public void setResolved(boolean resolved){
		this.resolved = resolved;
	}
	
	public String getType(){
		return type;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getDay(){
		return day;
	}
	
	public int getYear(){
		return year;
	}
	
	public int setMonth(){
		return month;
	}
	
	public int setDay(){
		return day;
	}
	
	public int setYear(){
		return year;
	}
	
	public void setType(String type){
		this.type = type;
	}
	public String[] getListOfCategories(){
		return category;
	}
	
	public int sizeOfCategoryList(){
		return category.length;
	}
	/**
	 * TODO setter for coordinate location
	 */
}