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
	private int ID;
	private String location;
		
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
	public Item(int ID, String name, String description, Member owner, boolean status, boolean resolved, String type, int month, int day, int year, String location){
		this.ID = ID;
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
	
	public boolean getResolved(){
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
	
	public String getLocation(){
		return location;
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
	
	public int getID(){
		return ID;
	}
	/**
	 * TODO setter for coordinate location
	 */
}