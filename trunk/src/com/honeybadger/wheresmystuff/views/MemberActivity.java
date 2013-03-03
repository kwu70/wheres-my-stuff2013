package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.Item;
import com.honeybadger.wheresmystuff.support.Member;
import com.honeybadger.wheresmystuff.support.Security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * MemberActivity contains the UI screen members are shown after successfully 
 * logging in.
 */
public class MemberActivity extends Activity{
	
	//Intent to go to addItemActivity
	private Intent addItem;
	
	//current Users email
	private String userEmail;
	
	//current user
	private Member currentMember;
	
	//used in the ListViews to properly display
	//the lost items and found items
	private ArrayAdapter<String> adapterFound;
	private ArrayAdapter<String> adapterLost;
	
	/**
	 * Called when the activity is first created.
	 * 
	 * @param state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_view);
		
		adapterFound = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1);
		adapterLost = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		
		
		Intent intent = getIntent();
		userEmail = intent.getExtras().getString("userEmail");
		currentMember = Security.getMember(userEmail);
				
		addItem = new Intent(this, AddItemActivity.class);
		addItem.putExtra("userEmail", userEmail);
		
		ListView foundList = (ListView) findViewById(R.id.listFound);
		ListView lostList = (ListView) findViewById(R.id.listLost);
		
		foundList.setAdapter(adapterFound);
		lostList.setAdapter(adapterLost);
		
		findViewById(R.id.btnAddItem).setOnClickListener(new AddItemClickListener());
		
		//Iterates through the members items and if
		//they have any then it assigns them to the appropriate
		//adapter
		if(currentMember.getItems() != null){
			for(Item item: currentMember.getItems()){
				if(item.getStatus() == false){
					adapterLost.add(item.getName());
				}
				else {
					adapterFound.add(item.getName());
				}
			}
		}
		
	}
	
	/*
	 * This method is called when the back button is pressed
	 * and returns to the previous activity
	 */
	public void onBackPressed() {
	    Intent i = new Intent(this, LoginView.class);
	    startActivity(i);
	    finish();
	}
	
	private class AddItemClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			startActivity(addItem);
		}
		
	}
}
