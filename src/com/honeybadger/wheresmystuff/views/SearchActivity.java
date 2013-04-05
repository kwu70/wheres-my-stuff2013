package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.Search;
import com.honeybadger.wheresmystuff.support.Item;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

public class SearchActivity extends Activity{

	private EditText s_editText;

	//used in the ListViews 
	private ArrayAdapter<String> adapterItems;
	private ArrayAdapter<String> adapterTemp;
	private ListView itemsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_view);

		//adapterItems is the Item List that is displayed on the screen
		adapterItems = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		//temp for changing adapter item.
		adapterTemp = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);

		//item List array of strings that appears on screen
		itemsList = (ListView) findViewById(R.id.search_listView);
		itemsList.setAdapter(adapterItems);
		
		s_editText = (EditText) findViewById(R.id.search_editText);

		//Button listener for Search
		findViewById(R.id.btnItemSearch).setOnClickListener(new SearchItemClickListener());
	}

	/**
	 * This class is a listener for the Search button and when it is clicked 
	 * it searches the database based on the radio button (either name or category)
	 * and produces a list below.
	 */
	private class SearchItemClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			adapterTemp.clear();
			String searchCriteria = s_editText.getText().toString();

			RadioButton name = (RadioButton)findViewById(R.id.radioName);
			RadioButton category = (RadioButton)findViewById(R.id.radioCategory);

			if(TextUtils.isEmpty(searchCriteria)) {
				s_editText.setError(getString(R.string.error_field_required));
				s_editText.requestFocus();
				
			}
			else {
				if(name.isChecked()){
					//search by name
					ArrayList<Item> tempNames = Search.searchByName(searchCriteria);
					if(tempNames != null){
						for(Item item: tempNames){
							adapterTemp.add(item.getName());
						}
					}
				} else if(category.isChecked()){
					//search by category
					ArrayList<Item> tempCategories = Search.searchByCategory(searchCriteria);
					if(tempCategories != null){
						for(Item item: tempCategories){
							adapterTemp.add(item.getName());
						}
					}
				}

				adapterItems = adapterTemp;
				itemsList.setAdapter(adapterItems);
			}
			
		}
	}
}



