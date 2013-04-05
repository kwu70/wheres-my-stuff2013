package com.honeybadger.wheresmystuff.support;

import java.util.ArrayList;

public class Search {
	
	/**
	 * Filters member's items to show only those that have a given category
	 * 
	 * @param member A member
	 * @param category String representing category
	 * @return matches the list that contains items of specified category
	 */
	public static ArrayList<Item> filterCategory(Member member, String category){
		ArrayList<Item> matches = new ArrayList<Item>();

		if((category == null) || (category.length() == 0)) {
			return matches;
		}
		else{
			ArrayList<Item> list = Security.getMemberItemList(member);
			
			for(int i = 0; i < list.size(); i++){
				Item currItem = list.get(i);
				if(currItem.getType().equals(category)){
					matches.add(list.get(i));
				}
			}
		}
		
		return matches;
	}
	
	/**
	 * Filters member's items to show only those that were posted as lost on a given date
	 * 
	 * @param member A member
	 * @param date String representing date
	 * @return matches the list that contains items lost on specified date
	 */
	public static ArrayList<Item> filterDate(Member member, String date){
		
		ArrayList<Item> list = Security.getMemberItemList(member);
		ArrayList<Item> matches = new ArrayList<Item>();
		
		if((null != date) && (date.length() == 0)) {
			return matches;
		}
		
		String[] mdy = date.split("/");
		int month = Integer.parseInt(mdy[0]);
		int day = Integer.parseInt(mdy[1]);
		int year = Integer.parseInt(mdy[2]);
		for(int i = 0; i < list.size(); i++){
			Item currItem = list.get(i);
			if(currItem.getMonth() >= month
					&& currItem.getDay() >= day
					&& currItem.getYear() >= year){
				matches.add(list.get(i));
			}
		}
		
		list = null;
		return matches;
	}
	
	/**
	 * Filters member's items to show only those that have a matching status
	 * 
	 * @param member A member
	 * @param status Boolean representing lost or found status
	 * @return matches the list that contains items with specified status
	 */
	public static ArrayList<Item> filterStatus(Member member, Boolean status){
		
		ArrayList<Item> list = Security.getMemberItemList(member);
		ArrayList<Item> matches = new ArrayList<Item>();
		
		for(int i = 0; i < list.size(); i++){
			Item currItem = list.get(i);
			if(currItem.getStatus() == status){
				matches.add(list.get(i));
			}else{
				return matches;
			}
		}
		
		list = null;
		return matches;
	}
	
	/**
	 * Filter items to show those with matching names
	 * 
	 * @param name The name of the particular item
	 * @return matches the list that contains items with the specified names
	 */
	public static ArrayList<Item> searchByName(String name){
		ArrayList<Item> list = Security.getItemList();
		ArrayList<Item> matches = new ArrayList<Item>();
		
		if(list==null) {
			return matches;
		}
		for(int i = 0; i<list.size();i++){
			Item cur = list.get(i);
			if(cur.getName().contains(name)){
				matches.add(list.get(i));
			}else{
				return matches;
			}
		}
		
		list = null;
		return matches;
	}
	
	/**
	 * Filter items by location.
	 * 
	 * @param location Item location
	 * @return matches A list of items in the specified location
	 */
	public static ArrayList<Item> searchByLocation(String location){
		ArrayList<Item> list = Security.getItemList();
		ArrayList<Item> matches = new ArrayList<Item>();
		
		if(list==null) {
			return matches;
		}
		for(int i = 0; i<list.size();i++){
			Item cur = list.get(i);
			if(cur.getLocation().contains(location)){
				matches.add(list.get(i));
			}
		}
		
		list = null;
		return matches;
		
	}
	
	/*
	 * given a String with an item name and location, return any matching items 
	 * from the list of items
	 * 
	 * @param s String containing the item name and location
	 * @return matches A list of items with the specified name and location
	 */
	public static ArrayList<Item> searchNameAndLocation(String s){
		ArrayList<Item> list = Security.getItemList();
		ArrayList<Item> matches = new ArrayList<Item>();
		
		String[] nl = s.split(" in ");
		String name = nl[0];
		String location = nl[1];
		
		if(list==null)
		{
			return matches;
		}
		for(int i =0;i<list.size();i++){
			Item cur = list.get(i);
			if(cur.getName().contains(name) && cur.getLocation().contains(location)){
				matches.add(list.get(i));
			}
		}
		
		list = null;
		return matches;
	}
	
	/*
	 * Search for an item by category.
	 * 
	 * @param category The category of the item being searched for
	 * @return matches A list of items with the same category as the search parameter
	 */
	public static ArrayList<Item> searchByCategory(String category){
		ArrayList<Item> list = Security.getItemList();
		ArrayList<Item> matches = new ArrayList<Item>();
		
		if(list==null)
		{
			return matches;
		}
		for(int i = 0;i<list.size();i++){
			Item cur = list.get(i);
			if(cur.getType().contains(category)){
				matches.add(list.get(i));
			}
		}
		return matches;
	}
}
