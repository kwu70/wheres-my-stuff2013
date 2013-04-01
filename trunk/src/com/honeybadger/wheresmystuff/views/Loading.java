package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;


public class Loading extends Activity {
	Button init;
	ProgressDialog pd;
	private int pdStatus = 0;
	private Handler pdHandler = new Handler();
	private long progress = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);
		addButtonListener();
	}
	public void addButtonListener(){
		init = (Button)findViewById(R.id.sign_in_button);
		
		init.setOnClickListener(
				new OnClickListener(){
					public void OnClick(View v){
						pd = new ProgressDialog(v.getContext());
						pd.setCancelable(true);
						pd.setMessage("Loading...");
						pd.setProgress(0);
						pd.setMax(100);
						pd.show();
						
						//reset load status
						pdStatus = 0;
						progress = 0;
						
						new Thread(new Runnable(){
							public void run(){
								while(pdStatus<100){
									pdStatus = task();
									try{
										Thread.sleep(1000);
									} catch(InterruptedException e){
										e.printStackTrace();
									}
									//update load status
									pdHandler.post(new Runnable(){
										public void run(){
											pd.setProgress(pdStatus);
										}
									});
								}
								if(pdStatus>=100){
									try{
										Thread.sleep(2000);
									} catch(InterruptedException e) {
										e.printStackTrace();
									}
									//close the progress bar
									pd.dismiss();
								}
							}
						}).start();
					}

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
					}
				});
	}
	
	public int task(){
		while(progress<=10000){
			progress++;
			if(progress==10000){
				return 10;
			}else if(progress==20000){
				return 20;
			}else if(progress ==3000){
				return 30;
			}
		}
		return 100;
	}
}
