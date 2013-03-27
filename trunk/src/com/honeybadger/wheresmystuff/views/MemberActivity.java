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
import android.widget.Toast;

/**
 * MemberActivity contains the UI screen members are shown after successfully 
 * logging in.
 */
public class MemberActivity extends Activity{
	
	//Intent to go to addItemActivity
	private Intent addItem;
	private Intent adminSettings;
	
	//current Users email
	private String userEmail;
	
	//current user
	private Member currentMember;
	
	private EditText date;
	
	//used in the ListViews to properly display
	//the lost items and found items
	private ArrayAdapter<String> adapterItems;
	private ArrayAdapter<String> adapterTemp;
	private ListView itemsList;
	
	private Spinner spinner;
	
	/**
	 * Called when the activity is first created.
	 * 
	 * @param state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_view);
		
		adapterItems = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		
		adapterTemp = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		
		
		Intent intent = getIntent();
		userEmail = intent.getExtras().getString("userEmail");
		currentMember = Security.getMember(userEmail);
		
		//Admin Settings Button shows if the user is an Admin
		View btn = (Button) findViewById(R.id.btnAdmin);
		if(currentMember instanceof Admin){
			btn.setVisibility(0);
		}
		
		//date
		date = (EditText) findViewById(R.id.txtDateChooser);	
		
		findViewById(R.id.date_button).setOnClickListener(new DateClickListener());
		
		spinner = (Spinner) findViewById(R.id.category_spinner);
		spinner.setOnItemSelectedListener(new CategorySpinnerOnItemSelectedListener());
		
		//Create new Admin Setting intent pass userEmail
		adminSettings = new Intent(this, AdminSettingActivity.class);
		adminSettings.putExtra("userEmail", userEmail);
		
		//Create Add Item Intent
		addItem = new Intent(this, AddItemActivity.class);
		addItem.putExtra("userEmail", userEmail);
		
		//item List
		itemsList = (ListView) findViewById(R.id.listFound);
		itemsList.setAdapter(adapterItems);
		
		//Button listener
		findViewById(R.id.btnAddItem).setOnClickListener(new AddItemClickListener());
		
		//Adds ClickListener to AdminSetting Button
		findViewById(R.id.btnAdmin).setOnClickListener(new AdminSettingClickListener());
		
		//Iterates through the members items and if
		//they have any then it assigns them to the appropriate
		//adapter
		if(currentMember.getItems() != null){
			for(Item item: currentMember.getItems()){
					adapterItems.add(item.getName());
			}
		}
		
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
	 * Date Click Listener for filtering by the date
	 *
	 */
	private class DateClickListener implements OnClickListener{

		public void onClick(View arg0) {
			
			String tempDate = date.getText().toString();
			adapterTemp.clear();
			
			ArrayList<Item> tempItemList;
			tempItemList = Search.filterCategory(currentMember, tempDate);
			
			if(tempItemList != null){
				for(Item item: tempItemList){
						adapterTemp.add(item.getName());
				}
			}
			
			//Update UI
			adapterItems = adapterTemp;
			itemsList.setAdapter(adapterItems);
		}
	}
	
	/**
	 * AdminSetting Listener for the Admin Setting Button only visible to Admins
	 * Once button is clicked takes user to AdminSettingActivity
	 */
	private class AdminSettingClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			startActivity(adminSettings);
		}
	}
	
	
	/**
	 * Spinner Filter
	 */
	private class CategorySpinnerOnItemSelectedListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			Toast.makeText(parent.getContext(), 
					"Filter By : " + parent.getItemAtPosition(pos).toString(),
					Toast.LENGTH_SHORT).show();
			
			adapterTemp.clear();
			ArrayList<Item> tempItemList = new ArrayList<Item>();
			String spinnerString = parent.getItemAtPosition(pos).toString();
			
			//Show everything
			if(spinnerString.equals("All")){
				tempItemList = currentMember.getItems();
				
				if(tempItemList != null){
					for(Item item: tempItemList){
							adapterTemp.add(item.getName());
					}
				}
				
			}
			else{
				//Filter by lost and found
				if(spinnerString.equals("Found") || spinnerString.equals("Lost")){
					if(spinnerString.equals("Found")){
						tempItemList = Search.filterStatus(currentMember, true);
					}
					else{
						tempItemList = Search.filterStatus(currentMember, false);
					}
				}
				//Filter by category
				else{
					tempItemList = Search.filterCategory(currentMember, spinnerString);
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
