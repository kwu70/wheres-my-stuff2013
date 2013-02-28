package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.Member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * MemberActivity contains the UI screen members are shown after successfully 
 * logging in.
 */
public class MemberActivity extends Activity{
	
	private Intent addItem;
	private String userEmail;
	private Member currentMember;
	
	/**
	 * Called when the activity is first created.
	 * 
	 * @param state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_view);
		
		Intent intent = getIntent();
		String userEmail = intent.getExtras().getString("userEmail");
				
		addItem = new Intent(this, AddItemActivity.class);
		
		findViewById(R.id.btnAddItem).setOnClickListener(new AddItemClickListener());
	}
	
	public void onBackPressed() {
	    Intent i = new Intent(this, LoginView.class);
	    startActivity(i);
	}
	
	private class AddItemClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			startActivity(addItem);
		}
		
	}
}
