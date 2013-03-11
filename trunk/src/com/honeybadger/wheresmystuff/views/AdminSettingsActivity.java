package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.Admin;
import com.honeybadger.wheresmystuff.support.Item;
import com.honeybadger.wheresmystuff.support.Member;
import com.honeybadger.wheresmystuff.support.Security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * AdminSettingsActivity contains the UI screen administrators
 * are shown after successfully logging in to the application.
 */
public class AdminSettingsActivity extends Admin{
	
	public AdminSettingsActivity(String email, String password) {
		super(email, password);
	}
	
	/**
	 * Intent to go (respectively) to removeMember, create Admin,
	 * and unlockAccount activities.
	 */
	private Intent removeMember, createAdmin, unlockAccount;
	
	//email of the current admin
	private String userEmail;
	
	//current admin
	private Admin currentAdmin;
	
	private ArrayAdapter<String> adaptorUsers;
	
	/**
	 * Called when the activity is first created.
	 * 
	 * @param state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_view);
			
		Intent intent = getIntent();
		userEmail = intent.getExtras().getString("userEmail");
		currentMember = Security.getMember(userEmail);
	
		
		//create remove member intent
		removeMember = new Intent();
		removeMember.putExtra("userEmail", userEmail);
		View btn1 = (Button) findViewById(R.id.btnRemoveMember);
		
		findViewById(R.id.btnRemoveMember).setOnClickListener(new RemoveMemberClickListener());
		
		//creates 'create admin' intent
		createAdmin = new Intent();
		createAdmin.putExtra("userEmail", userEmail);
		View btn2 =(Button) findViewById(R.id.btnCreateAdmin);
		
		findViewById(R.id.btnCreateAdmin).setOnClickListener(new CreateAdminClickListener());
		
		//create unlock account intent
		unlockAccount = new Intent();
		unlockAccount.putExtra("userEmail", userEmail);
		View btn3 = (Button) findViewById(R.id.btnUnlockAccount);
		
		findViewById(R.id.btnUnlockAccount).setOnClickListener(new UnlockAccountClickListener());
		
	}
	
	/*
	 * This method is called when the back button is pressed
	 * and returns to the previous activity
	 */
	public void onBackPressed() {
	    Intent i = new Intent();
	    startActivity(i);
	    finish();
	}
	
	private void finish() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * button listeners for admin activities
	 */
	private class RemoveMemberClickListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			startActivity(removeMember);
		}
		
	}
	private class CreateAdminClickListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			startActivity(createAdmin);
		}
		
	}
	private class UnlockAccountClickListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			startActivity(unlockAccount);
		}
		
	}
}
