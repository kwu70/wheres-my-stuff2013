package com.honeybadger.wheresmystuff.support;
import java.util.ArrayList;

public class Search {
	
	public static ArrayList<Item> filterCategory(Member member, String category){
		
	
		ArrayList<Item> list = security.getItemList();
		ArrayList<Item> matches = new ArrayList<Item>();
		for(i = 0; i < security.getItemListSize(); i++){
			Item currItem = list(i);
			if(currItem.getType().equalTo(currItem.getType())){
				matches.add(list(i));
			}
		}
		return matches;
	}
	
	public static ArrayList<Item> filterDate(Member member, String date){
		
		ArrayList<Item> list = security.getItemList();
		ArrayList<Item> matches = new ArrayList<Item>();
		for(i = 0; i < security.getItemListSize(); i++){
			Item currItem = list(i);
			if(currItem.getDay().equalTo(currItem.getDay())
					&& currItem.getMonth().equalTo(currItem.getMonth())
					&& currItem.getYear().equalTo(currItem.getYear())){
				matches.add(list(i));
			}
		}
		return matches;
	}
}
