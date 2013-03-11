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
		// TODO Auto-generated constructor stub
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
		adminEmail = intent.getExtras().getString("adminEmail");
		currentAdmin = Security.getMember(adminEmail);
		
		View btn = (Button) findViewById(R.id.btnAdmin);
		if(currentMember instanceof Admin){
			btn.setVisibility(1);
		}
		
		//create remove member intent
		removeMember = new Intent(this, RemoveMember.class);
		removeMember.putExtra("userEmail", userEmail);
		View btn = (Button) findViewById(R.id.btnRemoveMember);
		
		findViewById(R.id.btnRemoveMember).setOnClickListener(new removeMemberClickListener());
		
		//creates 'create admin' intent
		createAdmin = new Intent(this, CreateAdmin.class);
		createAdmin.putExtra("userEmail", userEmail);
		View btn =(Button) findViewById(R.id.btnCreateAdmin);
		
		findViewById(R.id.btnCreateAdmin).setOnClickListener(new createAdminClickListener());
		
		//create unlock account intent
		unlockAccount = new Intent(this, UnlockAccount.class);
		unlockAccount.putExtra("userEmail", userEmail);
		View btn = (Button) findViewById(R.id.btnUnlockAccount);
		
		findViewById(R.id.btnUnlockAccount).setOnClickListener(new unlockAccountClickListener());
		
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
	
	/*
	 * button listeners for admin activities
	 */
	private class removeMemberClickListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			startActivity(removeMember);
		}
		
	}
	private class createAdminClickListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			startActivity(createAdmin);
		}
		
	}
	private class unlockAccountClickListener implements OnClickListener{
		@Override
		public void onClick(View arg0) {
			startActivity(unlockAccount);
		}
		
	}
}
