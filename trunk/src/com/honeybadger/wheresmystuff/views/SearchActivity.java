package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.Member;
import com.honeybadger.wheresmystuff.support.Search;

import java.util.ArrayList;
import java.util.Calendar;
import android.app.Activity;
import android.app.SearchManager;
import android.os.Bundle;
import android.content.ClipData.Item;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity{

	private ListView listView1;

	private EditText editText1;

	//current user
	private Member currentMember;

	//intent to return to MemberActivity
	private Intent returnIntent;

	//used in the ListViews 
	private ArrayAdapter<String> adapterItems;
	private ArrayAdapter<String> adapterTemp;
	private ListView itemsList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_view);

		//item List array of strings that appears on screen
		itemsList = (ListView) findViewById(R.id.listView1);
		itemsList.setAdapter(adapterItems);

		//Button listener for Search
		findViewById(R.id.btnSearchForItems).setOnClickListener(new SearchItemClickListener());
	}

	/**
	 * This class is a listener for the Search button and when it is clicked 
	 * it searches the database based on the radio button (either name or category)
	 * and produces a list below.
	 */
	private class SearchItemClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String searchCriteria = editText1.getText().toString();
			

			RadioButton name = (RadioButton)findViewById(R.id.radioName);
			RadioButton category = (RadioButton)findViewById(R.id.radioCategory);

			if(name.isChecked()){
				//search by name
				ArrayList<Item> tempNames = Search.searchByName(searchCriteria);
				for(Item item: tempNames){
					adapterTemp.add(item.getName());
				}
			}
			if(category.isChecked()){
				//search by category
				ArrayList<Item> tempCategories = Search.searchByCategory(searchCriteria);
				for(Item item: tempCategories){
					adapterTemp.add(item.getName());
				}
			}

			//gets currently selected search type
			RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
			int radioButtonID = radioGroup.getCheckedRadioButtonId();
			RadioButton rad = (RadioButton) findViewById(radioButtonID);
			String type = rad.getText().toString();

			finish();
		
			adapterItems = adapterTemp;
			itemsList.setAdapter(adapterItems);
		
		}
	}
	
	/**
	 * This method is called when the back button is pressed
	 * and returns to the previous activity
	 */
	public void onBackPressed() {
	    Intent i = new Intent(this, MemberActivity.class);
	    startActivity(i);
	    finish();
	}
	
	

}



