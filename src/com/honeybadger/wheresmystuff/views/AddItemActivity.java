package com.honeybadger.wheresmystuff.views;

import java.util.Calendar;
import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.Item;
import com.honeybadger.wheresmystuff.support.Member;
import com.honeybadger.wheresmystuff.support.Security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 
 * This class contains the proper text fields
 *  and radio buttons to create a new item.
 *  
 * @author Honey Badger
 * @version 1.0
 */
public class AddItemActivity extends Activity{

	//email of user that is accessing the app
	private String userEmail;
	
	//instance of user currently using the app
	private Member currentMember;
	
	//intent to return to MemberActivity
	private Intent returnIntent, pictureIntent;
	
	//Texts fields for the item's name and description.
	private EditText itemName;
	private EditText itemDescription;
	
	//current date
	private int month;
	private int day;
	private int year;

	/**
	 * Called when the activity is first created.
	 * Creates an Intent object which moves to MemberActivity.class
	 * and sends the user email that is being used, back to the class.
	 * 
	 * @param savedInstanceState state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item_view);
		
		Intent intent = getIntent();
		userEmail = intent.getExtras().getString("userEmail");
		
		currentMember = Security.getMember(userEmail);
		
		returnIntent = new Intent(this, MemberActivity.class);
		returnIntent.putExtra("userEmail", userEmail);
		
		pictureIntent = new Intent(this, PictureActivity.class);
		
		itemName = (EditText) findViewById(R.id.editItemName);
		itemDescription = (EditText) findViewById(R.id.editDescription);

		
		findViewById(R.id.btnCancel).setOnClickListener(new CancelClickListener());
		
		findViewById(R.id.btnAddItem).setOnClickListener(new AddClickListener());
		
		findViewById(R.id.picBtn).setOnClickListener(new PictureClickListener());
		
	}
	
	/**
	 * This class is a listener for the cancel button. When it is clicked
	 * it returns the user back to MemberActivity
	 * 
	 * @author Honey Badger
	 */
	private class CancelClickListener implements OnClickListener{

		/**
		 * Activity for detecting if cancel button is pressed or not
		 * @param v
		 */
		@Override
		public void onClick(View v) {
			startActivity(returnIntent);
			finish();
		}
		
	}
	
	/**
	 * This class is a listener for the add picture button. When it is clicked
	 * it takes the user to the add picture view
	 * 
	 * @author Honey Badger
	 */
	private class PictureClickListener implements OnClickListener{

		/**
		 * Activity for detecting if add picture button is pressed or not
		 * @param v
		 */
		@Override
		public void onClick(View v) {
			startActivity(pictureIntent);
			finish();
		}
	}
	
	/**
	 * This class is a listener for the Add Item button. 
	 * When it is clicked gets all the data from the fields
	 * and buttons and creates a new item. Then it goes back
	 * to member activity so that it can be displayed.
	 * 
	 * @author Honey Badger
	 */
	private class AddClickListener implements OnClickListener{
		
		/**
		 * 	The listener for Adding an Item
		 * 
		 * @param v
		 */
		@Override
		public void onClick(View v) {
			String name = itemName.getText().toString();
			String description = itemDescription.getText().toString();
			//false is lost and true is found
			Boolean lostFound = false;
			RadioButton radLost = (RadioButton)findViewById(R.id.radLost);
			
			if(radLost.isChecked()){
				lostFound = false;
			}
			else{
				lostFound = true;
			}
			//default false because the user is creating
			Boolean resolved = false;
			
			//gets currently selected type
			RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radGroupType);
			int radioButtonID = radioGroup.getCheckedRadioButtonId();
			RadioButton rad = (RadioButton) findViewById(radioButtonID);
			String arr[] = Item.getListOfCategories();
			String type = rad.getText().toString();
			for(String s: arr){
				if(type.equals(s)){
					type = s;
				}
			}
			
			//get date from system
			Calendar date = Calendar.getInstance();
			month = date.get(Calendar.MONTH) + 1;
			day = date.get(Calendar.DAY_OF_MONTH);
			year = date.get(Calendar.YEAR);

			
			
			//creates and adds a new item to the current members item list
			Security.addItem(new Item(Security.getCurrentID() ,name, description,currentMember, 
					lostFound, resolved, type, month, day, year, "Atlanta"));
			//goes back to member activity and displays item
			startActivity(returnIntent);
			finish();
		}
		
	}
}
