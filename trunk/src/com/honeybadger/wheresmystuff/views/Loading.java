package com.honeybadger.wheresmystuff.views; 

import com.honeybadger.wheresmystuff.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

public class Loading extends Activity {
	//a ProgressDialog object
	private ProgressDialog pd;

    /** Called when the activity is first created. */
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
			pd.setMessage("Loading application view, please wait");
			pd.setCancelable(true);
			pd.setIndeterminate(false);
			pd.setMax(100);
			pd.setProgress(0);
			pd.setIcon(R.drawable.honey_badger);
			pd.show();
			
		}

		//The code to be executed in a background thread.
		@Override
		protected Void doInBackground(Void... params) {
			try {
				//Get the current thread's token
				synchronized (this) {
					//Initialize an integer (that will act as a counter) to zero
					int counter = 0;
					//While the counter is smaller than four
					while(counter <= 4){
						//Wait 850 milliseconds
						this.wait(850);
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

		//Update the progress
		@Override
		protected void onProgressUpdate(Integer... values){ 
			pd.setProgress(values[0]);
		}
		
		//After executing the code in the thread
		@Override
		protected void onPostExecute(Void result) {
			//close the load screen
			pd.dismiss();
			setContentView(R.layout.member_view);
		}
    }
    
    @Override
    public void onBackPressed() {
    		super.onBackPressed();
    }
}