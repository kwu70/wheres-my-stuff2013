package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/*
 * RegisterActivity contains all the proper elements needed for a user to register.
 * It also delegates the proper items to the correct support classes
 */
public class RegisterActivity extends Activity{
	
	//Hold the Strings inside the texts fields in the activity
	private String email;
	private String password;
	private String confirmPassword;
		
	//Used to display a confirmation message to the user
	private AlertDialog.Builder builder;
	
	//Are a code representation of the email, password and confirmation
	//text boxes
	private EditText eEmail;
	private EditText ePassword;
	private EditText eConfirmPassword;
	
	//Used to declare the intent to go back to login activity after the user has registered
	private Intent i;
			
	/*
	 * When the activity is created, the intent is initialized, the builder is initialized, and the
	 * on click listener is created.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_view);
		
	    i = new Intent(this, LoginView.class);
	    	    
	    builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure?").setPositiveButton("Yes", new DialogClickListener())
	    .setNegativeButton("No", new DialogClickListener());

		
		eEmail = (EditText) findViewById(R.id.txtEmail);
		
		ePassword = (EditText) findViewById(R.id.txtPassword);
		eConfirmPassword = (EditText) findViewById(R.id.txtConfirm);
		
		findViewById(R.id.btnRegister).setOnClickListener(new RegisterClickListener());
		
	}
	
	//When back button is pressed, the screen goes back to the login screen
	public void onBackPressed() {
	  	startActivity(i);
	}
	
	
	private class RegisterClickListener implements OnClickListener{

		/*
		 * Gets the text from all three text fields and
		 * checks to make sure they meet the correct formats and password is equal to
		 * confirm password and shows the display box asking for
		 * confirmation to create the user.
		 */
		@Override
		public void onClick(View arg0) {
			email = eEmail.getText().toString();
			password = ePassword.getText().toString();
			confirmPassword = eConfirmPassword.getText().toString();
			
			boolean cancel = false;
			View focusView = null;
			
			//Check for a valid password
			if (TextUtils.isEmpty(password)) {
				ePassword.setError(getString(R.string.error_field_required));
				focusView = ePassword;
				cancel = true;
			} else if (password.length() < 4) {
				ePassword.setError(getString(R.string.error_invalid_password));
				focusView = ePassword;
				cancel = true;
			}

			// Check for a valid email address.
			if (TextUtils.isEmpty(email)) {
				eEmail.setError(getString(R.string.error_field_required));
				focusView = eEmail;
				cancel = true;
			} else if (!email.contains("@")) {
				eEmail.setError(getString(R.string.error_invalid_email));
				focusView = eEmail;
				cancel = true;
			}
			
			if (cancel) {
				// There was an error; don't attempt login and focus the first
				// form field with an error.
				focusView.requestFocus();
			}
			else {
				if(password.equals(confirmPassword)){
					builder.show();
				}
				else{
					ePassword.setError(getString(R.string.error_no_match));
				}
			}
		}
		
	}
	
	private class DialogClickListener implements DialogInterface.OnClickListener{

		/*
		 * If the user clicks yes on the display box, the activity
		 * creates a new member and starts the login activity
		 * If they click no, then the message goes away  and nothing
		 * happens
		 */
		@Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	startActivity(i);
	        	Security.addMember(email, password); //(register's new member)
	        	finish();
	        	break;
	        case DialogInterface.BUTTON_NEGATIVE:
	        	break;
	        }
	    }
		
	}
}