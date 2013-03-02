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
import android.widget.RadioGroup;

public class AddItemActivity extends Activity{
	
	private String userEmail;
	private Member currentMember;
	private Intent returnIntent;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item_view);
		
		Intent intent = getIntent();
		userEmail = intent.getExtras().getString("userEmail");
		
		currentMember = Security.getMember(userEmail);

		
		returnIntent = new Intent(this, MemberActivity.class);
		
		findViewById(R.id.btnCancel).setOnClickListener(new CancelClickListener());
		
		findViewById(R.id.btnAddItem).setOnClickListener(new AddClickListener());
	}
	
	private class CancelClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			startActivity(returnIntent);
		}
		
	}
	
	private class AddClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String name = findViewById(R.id.editItemName).toString();
			String description = findViewById(R.id.editDescription).toString();
			//false is lost and true is found
			Boolean lostFound;
			if(findViewById(R.id.radFound).isPressed()){
				lostFound = true;
			}
			else{
				lostFound = false;
			}
			//defalut false because the user is creating
			Boolean resolved = false;
			
			//gets currently selected type
			RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radGroupType);
			int radioButtonID = radioGroup.getCheckedRadioButtonId();
			String type = findViewById(radioButtonID).toString();
			
			//creates and adds a new item to the current members item list
			currentMember.addItem(new Item(name, description,currentMember, lostFound,resolved,type));
			
			//goes back to member activity and displays item
			startActivity(returnIntent);
		}
		
	}
}
