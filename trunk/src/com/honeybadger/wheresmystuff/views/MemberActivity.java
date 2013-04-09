package com.honeybadger.wheresmystuff.views;

import java.util.ArrayList;

import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.Admin;
import com.honeybadger.wheresmystuff.support.Item;
import com.honeybadger.wheresmystuff.support.Member;
import com.honeybadger.wheresmystuff.support.Search;
import com.honeybadger.wheresmystuff.support.Security;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * MemberActivity contains the UI screen members are shown after successfully 
 * logging in.
 * 
 * List of Items that the user has added.
 * Filtering of that list of items
 * 	By Category
 * 	By Status
 * 	By Date
 * Admin Settings if the Member is an Admin
 * Search Button which takes user to search screen
 * Add Item Button which takes user to add item screen
 * 
 */
public class MemberActivity extends Activity{
	
	//Intent to go to different pages
	private Intent addItem;
	private Intent adminSettings;
	private Intent search;
	
	//current User's email
	private String userEmail;
	
	//current user
	private Member currentMember;
	
	//Date Textfield for filtering by date
	private EditText date;
	
	//used in the ListViews to properly display
	//the lost items and found items
	private ArrayAdapter<String> adapterItems;
	private ArrayAdapter<String> adapterTemp;
	private ListView itemsList;
	
	//Drop down box for Filtering
	private Spinner spinner;
	
	/**
	 * Called when the activity is first created.
	 * 
	 * @param state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_view);
		
		//adapterItems is the Item List that is displayed on the screen
		adapterItems = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		//temp for changing adapter item.
		adapterTemp = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		
		//Setting up Intents to go to other activities
		Intent intent = getIntent();
		userEmail = intent.getExtras().getString("userEmail");
		currentMember = Security.getMember(userEmail);
		
		//Create new Admin Setting intent pass userEmail
		adminSettings = new Intent(this, AdminSettingActivity.class);
		adminSettings.putExtra("userEmail", userEmail);
		
		//Create Add Item Intent
		addItem = new Intent(this, AddItemActivity.class);
		addItem.putExtra("userEmail", userEmail);
		
		//Create Search Intent
		search = new Intent(this, SearchActivity.class);
		search.putExtra("userEmail", userEmail);
		
		//Admin Settings Button visible if the user is an Admin
		View btn = (Button) findViewById(R.id.btnAdmin);
		if(currentMember instanceof Admin){
			btn.setVisibility(0);
		}
		
		//Date filtering setup and actionlistener
		date = (EditText) findViewById(R.id.txtDateChooser);	
		findViewById(R.id.date_button).setOnClickListener(new DateClickListener());
		
		//Drop down box for filtering set up
		spinner = (Spinner) findViewById(R.id.category_spinner);
		spinner.setOnItemSelectedListener(new CategorySpinnerOnItemSelectedListener());
		
		//item List array of strings that appears on screen
		itemsList = (ListView) findViewById(R.id.listFound);
		itemsList.setAdapter(adapterItems);
		
		//Button listener for Add Item
		findViewById(R.id.btnAddItem).setOnClickListener(new AddItemClickListener());
		
		//ClickListener to AdminSetting Button
		findViewById(R.id.btnAdmin).setOnClickListener(new AdminSettingClickListener());
		
		//ClickListener for Search Button
		findViewById(R.id.btnSearch).setOnClickListener(new SearchClickListener());
		
		//Iterates through the members items and if
		//they have any then it assigns them to the appropriate
		//adapter
		
	}
	
	/**
	 * This method is called when the back button is pressed
	 * and returns to the previous activity
	 */
	public void onBackPressed() {
	    Intent i = new Intent(this, LoginView.class);
	    startActivity(i);
	    finish();
	}
	
	/**
	 * AddItem Listener for the Add Item Button
	 * Once button is clicked takes user to AddItemActivity
	 */
	private class AddItemClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			startActivity(addItem);
		}
		
	}
	
	/**
	 * Search Listener for the Search for Items Button
	 * Once button is clicked takes user to Search Activity where they
	 * can search for Items.
	 */
	private class SearchClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			startActivity(search);	
		}
		
	}
	
	/**
	 * Date Click Listener for filtering by the date.
	 * Gets the string from the textfield and puts it into filter method
	 * updates the UI item list to show only dates that are before or equal
	 * to the date entered into the textfield.
	 */
	private class DateClickListener implements OnClickListener{

		public void onClick(View arg0) {
			//clear temp and get date
			String tempDate = date.getText().toString();
			adapterTemp.clear();
			//filter the list
			ArrayList<Item> tempItemList;
			tempItemList = (ArrayList<Item>) Search.filterDate(currentMember, tempDate);
			//add filtered items into new list
			if(tempItemList != null){
				for(Item item: tempItemList){
						System.out.println(item.getName());
						adapterTemp.add(item.getName());
				}
			}
			//Update UI 
			adapterItems = adapterTemp;
			itemsList.setAdapter(adapterItems);
		}
	}
	
	/**
	 * AdminSetting Listener for the Admin Setting Button. Only visible to Admins.
	 * Once button is clicked takes user to AdminSettingActivity.
	 */
	private class AdminSettingClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			startActivity(adminSettings);
		}
	}
	
	
	/**
	 * SpinnerFilter filters the item list by category or status,
	 * then sends the string in drop down box to filtering method
	 * and updates the UI with the filtered array.
	 */
	private class CategorySpinnerOnItemSelectedListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			//clear and get category
			adapterTemp.clear();
			ArrayList<Item> tempItemList = new ArrayList<Item>();
			String spinnerString = parent.getItemAtPosition(pos).toString();
			
			//Show all items in member's item list
			if(spinnerString.equals("All")){
				ArrayList<Item> temp = Security.getMemberItemList(currentMember);
				
				if(temp != null){
					for(Item item: temp){
							adapterTemp.add(item.getName());
					}
				}				
			}
			else{
				//Filter by lost and found
				if(spinnerString.equals("Found") || spinnerString.equals("Lost")){
					if(spinnerString.equals("Found")){
						tempItemList = (ArrayList<Item>) Search.filterStatus(currentMember, true);
					}
					else{
						tempItemList = (ArrayList<Item>) Search.filterStatus(currentMember, false);
					}
				}
				//Filter by category
				else{
					tempItemList = (ArrayList<Item>) Search.filterCategory(currentMember, spinnerString);
				}
				//make the new array adapter for the list
				if(currentMember.getItems() != null){
					for(Item item: tempItemList){
							adapterTemp.add(item.getName());
					}
				}
			}
			//update the UI
			adapterItems = adapterTemp;
			itemsList.setAdapter(adapterItems);
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			if(currentMember.getItems() != null){
				for(Item item: currentMember.getItems()){
						adapterItems.add(item.getName());
				}
			}
			itemsList.setAdapter(adapterItems);	
		}
		
		
	}
}
