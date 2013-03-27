package com.honeybadger.wheresmystuff.support;
import java.util.ArrayList;

public class Search {
	
	public static ArrayList<Item> filterCategory(Member member, String category){
		ArrayList<Item> matches = new ArrayList<Item>();

		if((category == null) || (category.length() == 0)) {
			
		}
		else{
			ArrayList<Item> list = Security.getMemberItemList(member);
			
			for(int i = 0; i < Security.getItemListSize(); i++){
				Item currItem = list.get(i);
				if(currItem.getType().equals(category)){
					matches.add(list.get(i));
				}
			}
		}
		return matches;
	}
	
	public static ArrayList<Item> filterDate(Member member, String date){
		
		if((null != date) && (date.length() == 0)) {
			throw new NullPointerException("Date is null.");
		}
		
		ArrayList<Item> list = Security.getMemberItemList(member);
		ArrayList<Item> matches = new ArrayList<Item>();
		
		String[] mdy = date.split("/");
		int month = Integer.parseInt(mdy[0]);
		int day = Integer.parseInt(mdy[1]);
		int year = Integer.parseInt(mdy[2]);
		for(int i = 0; i < Security.getItemListSize(); i++){
			Item currItem = list.get(i);
			if(currItem.getMonth() == month
					&& currItem.getDay() == day
					&& currItem.getYear() == year){
				matches.add(list.get(i));
			}
		}
		return matches;
	}
	public static ArrayList<Item> filterStatus(Member member, Boolean status){
		
		ArrayList<Item> list = Security.getMemberItemList(member);
		ArrayList<Item> matches = new ArrayList<Item>();
		
		for(int i = 0; i < Security.getItemListSize(); i++){
			Item currItem = list.get(i);
			if(currItem.getStatus() == status){
				matches.add(list.get(i));
			}
		}
		return matches;
	}
}
