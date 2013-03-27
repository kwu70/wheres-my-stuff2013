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
			return null;
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
		
		if((null != date) && (date.length() == 0)) {
			return null;
		}
		
		ArrayList<Item> list = Security.getMemberItemList(member);
		ArrayList<Item> matches = new ArrayList<Item>();
		
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
			}
		}
		return matches;
	}
}
