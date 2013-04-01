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
		
		final ProgressDialog pd = new ProgressDialog(load.xml);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setIndeterminate(true);
		pd.setCancelable(true);
		
		findViewById(R.id.btnRegister).setOnClickListener(new loadListener());
		
		private class loadListener implements OnClickListener{
				@Override
				public void OnClick(View arg0){
				pd.show();
			}
		}
	}
}
		