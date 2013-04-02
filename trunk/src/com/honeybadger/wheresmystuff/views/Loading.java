package com.honeybadger.wheresmystuff.views; // 41 Post - Created by DimasTheDriver on Jan/26/2012 . Part of the 'Android: how to create a loading screen – Part 3' post. Available at: http://www.41post.com/?p=4650 

import com.honeybadger.wheresmystuff.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

public class Loading extends Activity {
	//creates a ViewSwitcher object, to switch between Views
	private ViewSwitcher viewSwitcher;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		//Initialize a LoadViewTask object and call the execute() method
        new LoadViewTask().execute();
    }
    
    //subclass for AsyncTask
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>{
    	//A ProgressBar object
    	private ProgressBar pb_progressBar;
    	
    	//Before running code in the separate thread
		@Override
		protected void onPreExecute() 
		{
			//Initialize the ViewSwitcher object
	        viewSwitcher = new ViewSwitcher(Loading.this);
	        /* Initialize the loading screen with data from the 'loadingscreen.xml' layout xml file. 
	         * Add the initialized View to the viewSwitcher.*/
			viewSwitcher.addView(ViewSwitcher.inflate(Loading.this, R.layout.load, null));
			
			//Initialize ProgressBar instance
			pb_progressBar = (ProgressBar) viewSwitcher.findViewById(R.id.pb_progressbar);
			//Sets the maximum value to 100 			
			pb_progressBar.setMax(100);
			
			//Set ViewSwitcher instance as the current View.
			setContentView(viewSwitcher);
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

		//Update the progress at progress bar
		@Override
		protected void onProgressUpdate(Integer... values){ 
			//Update the progress at the UI if progress value is smaller than 100
			if(values[0] <= 100){
				pb_progressBar.setProgress(values[0]);
			}
		}
		
		//After executing the code in the thread
		@Override
		protected void onPostExecute(Void result) 
		{
			/* Initialize the application's main interface from the 'main.xml' layout xml file. 
	         * Add the initialized View to the viewSwitcher.*/
			viewSwitcher.addView(ViewSwitcher.inflate(Loading.this, R.layout.load, null));
			//Switch the Views
			viewSwitcher.showNext();
		}
    }
    
    @Override
    public void onBackPressed() {
    		//Finishes the current Activity
    		super.onBackPressed();
    }
}