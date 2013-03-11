package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.Item;
import com.honeybadger.wheresmystuff.support.Member;
import com.honeybadger.wheresmystuff.support.Admin;

import com.honeybadger.wheresmystuff.support.Security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/*
 * This class contains the proper text fields and radio buttons to create a new item.
 */
public class RemoveMember extends Activity{
	
	//email of user that is accessing the app
	private String userEmail;
	
	//instance of user currently using the app
	private Member currentMember;
	
	//intent to return to MemberActivity
	private Intent returnIntent;
	
	//Texts fields for user to remove
	private EditText removeEmail;
	
	/**
	 * Called when the activity is first created.
	 * Creates Intent object which moves to MemberActivity.class.
	 * and sends the user email that is being used, back to the class.
	 * 
	 * @param state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remove_member_view);
		
		Intent intent = getIntent();
		userEmail = intent.getExtras().getString("userEmail");
		
		currentMember = Security.getMember(userEmail);
		
		returnIntent = new Intent(this, MemberActivity.class);
		returnIntent.putExtra("userEmail", userEmail);
		
		removeEmail = (EditText) findViewById(R.id.removeEmail);
		
		findViewById(R.id.btnCancel).setOnClickListener(new CancelClickListener());
		
		findViewById((Integer) R.id.btnRemoveMember).setOnClickListener(new RemoveMemberClickListener());
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
	 * all the data from the fields and buttons and removes the member. Then it goes back
	 * to member activity so that it can be displayed.
	 */
	private class RemoveMemberClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String name = removeEmail.getText().toString();
			
			//removes the member in question
			Security.removeMember(name);
			
			//goes back to member activity and displays item
			startActivity(returnIntent);
			finish();
		}
		
	}
}
