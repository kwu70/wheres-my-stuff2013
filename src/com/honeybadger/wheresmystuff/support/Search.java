package com.honeybadger.wheresmystuff.support;
import java.util.ArrayList;

public class Search {
	
	public static ArrayList<Item> filterCategory(Member member, String category){
		
	
		ArrayList<Item> list = Security.getItemList();
		ArrayList<Item> matches = new ArrayList<Item>();
		for(int i = 0; i < Security.getItemListSize(); i++){
			Item currItem = list.get(i);
			if(currItem.getType().equals(category)){
				matches.add(list.get(i));
			}
		}
		return matches;
	}
	
	public static ArrayList<Item> filterDate(Member member, String date){
		
		ArrayList<Item> list = Security.getItemList();
		ArrayList<Item> matches = new ArrayList<Item>();
		
		String[] mdy = date.split("/");
		int month = Integer.parseInt(mdy[0]);
		int day = Integer.parseInt(mdy[1]);
		int year = Integer.parseInt(mdy[2]);
		for(int i = 0; i < Security.getItemListSize(); i++){
			Item currItem = list.get(i);
			if(currItem.getDay() == month
					&& currItem.getMonth() == day
					&& currItem.getYear() == year){
				matches.add(list.get(i));
			}
		}
		return matches;
	}
	public static ArrayList<Item> filterStatus(Member member, Boolean status){
		
		
		ArrayList<Item> list = Security.getItemList();
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
