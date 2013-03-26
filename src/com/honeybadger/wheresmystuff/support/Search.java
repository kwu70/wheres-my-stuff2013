package com.honeybadger.wheresmystuff.support;
import java.util.ArrayList;

public class Search {
	
	public static ArrayList<Item> filterCategory(Member member, String category){
		
	
		ArrayList<Item> list = Security.getItemList();
		ArrayList<Item> matches = new ArrayList<Item>();
		for(int i = 0; i < Security.getItemListSize(); i++){
			Item currItem = list.get(i);
			if(currItem.getType().equals(currItem.getType())){
				matches.add(list.get(i));
			}
		}
		return matches;
	}
	
	public static ArrayList<Item> filterDate(Member member, String date){
		
		ArrayList<Item> list = Security.getItemList();
		ArrayList<Item> matches = new ArrayList<Item>();
		for(int i = 0; i < Security.getItemListSize(); i++){
			Item currItem = list.get(i);
			if(currItem.getDay() == currItem.getDay()
					&& currItem.getMonth() == currItem.getMonth()
					&& currItem.getYear() == currItem.getYear()){
				matches.add(list.get(i));
			}
		}
		return matches;
	}
}
