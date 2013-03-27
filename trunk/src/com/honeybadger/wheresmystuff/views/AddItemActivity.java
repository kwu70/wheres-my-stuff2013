package com.honeybadger.wheresmystuff.views;

import java.util.Calendar;

import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.Item;
import com.honeybadger.wheresmystuff.support.Member;
import com.honeybadger.wheresmystuff.support.Security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/*
 * This class contains the proper text fields and radio buttons to create a new item.
 */
public class AddItemActivity extends Activity{
	
	//email of user that is accessing the app
	private String userEmail;
	
	//instance of user currently using the app
	private Member currentMember;
	
	//intent to return to MemberActivity
	private Intent returnIntent;
	
	//Texts fields for the item's name and description.
	private EditText itemName;
	private EditText itemDescription;
	
	//current date
	private int month;
	private int day;
	private int year;
	
	/**
	 * Called when the activity is first created.
	 * Creates Intent object which moves to MemberActivity.class.
	 * and sends the user email that is being used, back to the class.
	 * 
	 * @param state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item_view);
		
		Intent intent = getIntent();
		userEmail = intent.getExtras().getString("userEmail");
		
		currentMember = Security.getMember(userEmail, this);
		
		returnIntent = new Intent(this, MemberActivity.class);
		returnIntent.putExtra("userEmail", userEmail);
		
		itemName = (EditText) findViewById(R.id.editItemName);
		itemDescription = (EditText) findViewById(R.id.editDescription);

		
		findViewById(R.id.btnCancel).setOnClickListener(new CancelClickListener());
		
		findViewById(R.id.btnAddItem).setOnClickListener(new AddClickListener());
		
	}
	
	/*
	 * This class is a listener for the cancel button and when it is clicked
	 * just returns back to MemberActivity
	 */
	private class CancelClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			startActivity(returnIntent);
			finish();
		}
		
	}
	
	/*
	 * This class is a listener for the Add Item button and when it is clicked gets 
	 * all the data from the fields and buttons and creates a new item. Then it goes back
	 * to member activity so that it can be displayed.
	 */
	private class AddClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String name = itemName.getText().toString();
			String description = itemDescription.getText().toString();
			//false is lost and true is found
			Boolean lostFound;
			RadioButton radLost = (RadioButton)findViewById(R.id.radLost);
			
			if(radLost.isChecked()){
				lostFound = false;
			}
			else{
				lostFound = true;
			}
			//defalut false because the user is creating
			Boolean resolved = false;
			
			//gets currently selected type
			RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radGroupType);
			int radioButtonID = radioGroup.getCheckedRadioButtonId();
			RadioButton rad = (RadioButton) findViewById(radioButtonID);
			String type = rad.getText().toString();
			
			//get date from system
			Time date = new Time(Time.getCurrentTimezone());
			date.setToNow();
			
			month = date.month + 1;
			day = date.monthDay;
			year = date.year;
			
			
			
			//creates and adds a new item to the current members item list
			currentMember.addItem(new Item(Security.getDB().getCurrentItemID() ,name, description,currentMember, lostFound, resolved, type, month, day, year));
			
			//goes back to member activity and displays item
			startActivity(returnIntent);
			finish();
		}
		
	}
}
