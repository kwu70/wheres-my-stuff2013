package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * MemberActivity contains the UI screen members are shown after successfully 
 * logging in.
 */
public class MemberActivity extends Activity{
	
	/**
	 * Called when the activity is first created.
	 * 
	 * @param state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_view);
	}
	
	public void onBackPressed() {
	    Intent i = new Intent(this, LoginView.class);
	    startActivity(i);
	}
}
