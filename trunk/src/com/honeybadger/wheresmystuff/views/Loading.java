package com.honeybadger.wheresmystuff.views; 

import com.honeybadger.wheresmystuff.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

public class Loading extends Activity {
	//a ProgressDialog object
	private ProgressDialog pd;

    //Called when the activity is first created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LoadViewTask().execute();
    }
    
    //subclass for AsyncTask
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>{
    	
		@Override
		protected void onPreExecute() {
			//initialize a new instance of Progress Dialog and configure settings
			pd = new ProgressDialog(Loading.this);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setTitle("Loading...");
			pd.setMessage("Loading application, please wait");
			pd.setCancelable(true);
			pd.setIndeterminate(false);
			pd.setMax(100);
			pd.setProgress(0);
			pd.show();
			
		}

		/*
		 * Background task to run before loading the application
		 */
		@Override
		protected Void doInBackground(Void... params) {
			try {
				synchronized (this) {
					int counter = 0;
					while(counter <= 4){
						this.wait(1000);
						counter++;
						//Set the current progress. 
						publishProgress(counter*25);
					}
				}
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		//Update the progress bar
		@Override
		protected void onProgressUpdate(Integer... values){ 
			pd.setProgress(values[0]);
		}
		
		//After executing the code in the thread, it closes the load screen.
		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();
		}
    }
    
    @Override
    public void onBackPressed() {
    		super.onBackPressed();
    }
}