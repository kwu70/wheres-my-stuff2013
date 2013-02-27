package com.honeybadger.wheresmystuff.support;
/**
 * This class contains the array of possible categories of items that the Member can choose to list their item under
 */
public class Category {
	protected String[] type;
	private int size;
	
	/**
	 * Constructor for Category. Sets up array of the available types
	 */
	public Category(){
		type = new String[]{"Food", "Clothing", "Personal","Donation"};
	}
	
	/**
	 * getters/setters
	 */
	public String[] getListOfCategories(){
		return type;
	}
	
	public void setListOfCategories(String[] type){
		this.type = type;
	}
	
	public int size(){
		return size = type.length;
		
	}
}
