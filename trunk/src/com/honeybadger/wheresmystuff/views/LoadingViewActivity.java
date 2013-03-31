package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoadingViewActivity extends Activity{
	//Called when activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setIndeterminate(true);
		pd.setCancelable(true);
		
		Button b = (Button) findViewById(R.id.btnRegister);
		b.setOnClickListener(new OnClickListener(){
		
			@Override
			public void onClick(View arg0){
				pd.show();
			}
		});
	}
}
		