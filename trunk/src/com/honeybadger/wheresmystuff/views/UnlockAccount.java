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
public class UnlockAccount extends Activity{
	
	//email of user that is accessing the app
	private String userEmail, password;
	
	//instance of user currently using the app
	private Member currentMember;
	
	//intent to return to MemberActivity
	private Intent returnIntent;
	
	//Texts field for user account to unlock
	private EditText toUnlock;
	
	/**
	 * Called when the activity is first created.
	 * Creates Intent object which moves to MemberActivity.class.
	 * and sends the user email that is being used, back to the class.
	 * 
	 * @param state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.unlock_account_view);
		
		Intent intent = getIntent();
		userEmail = intent.getExtras().getString("userEmail");
		
		currentMember = Security.getMember(userEmail);
		
		returnIntent = new Intent(this, AdminSettingsActivity.class);
		returnIntent.putExtra("userEmail", userEmail);
		
		toUnlock = (EditText) findViewById(R.id.toUnlock);
		
		findViewById(R.id.btnCancel).setOnClickListener(new CancelClickListener());
		
		findViewById(R.id.btnUnlockAccount).setOnClickListener(new UnlockAccountClickListener());
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
	 * This class is a listener for the unlock account button and when it is clicked gets 
	 * all the data from the fields and buttons and unlocks the users account. Then it goes back
	 * to member activity so that it can be displayed.
	 */
	private class UnlockAccountClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String name = toUnlock.getText().toString();
			
			//creates and adds a new item to the current members item list
			Security s = new Security();
			s.resetAttempts(Security.getMember(name));
			
			//goes back to member activity and displays item
			startActivity(returnIntent);
			finish();
		}
		
	}
}
