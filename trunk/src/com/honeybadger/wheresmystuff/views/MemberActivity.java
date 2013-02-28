package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;
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
	
	/**
	 * Called when the activity is first created.
	 * 
	 * @param state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_view);
				
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
