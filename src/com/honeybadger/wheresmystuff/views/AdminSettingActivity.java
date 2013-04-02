package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.Security;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/**
 * Admin Setting Activity class is an activity for Admins that allows
 * them to manipulate the system variables.
 * Create a new admin
 * Unlock members
 * Remove members
 * 
 * @author Honey Badger
 */
public class AdminSettingActivity extends Activity{
	
	private String userEmail;
	
	private Intent memberActivity;
	
	//Textfields
	private EditText adminEmail;
	
	private EditText adminPassword;
	
	private EditText adminConfirmPassword;
	
	private EditText email;
	
	//Dialog boxes
	private AlertDialog.Builder builder;
	
	private AlertDialog.Builder remover;
	
	private AlertDialog.Builder unlocker;
	
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * @param savedInstanceState
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_settings_view);
		
		//gets the email of the current member
		Intent intent = getIntent();
		userEmail = intent.getExtras().getString("userEmail");
		
		//creating the intent to go back and passing in user email
		memberActivity = new Intent(this, MemberActivity.class);
		memberActivity.putExtra("userEmail", userEmail);
		
		//Getting all the textfields from admin_settings_view
		adminEmail = (EditText) findViewById(R.id.txtAdminSettingAdminEmail);
		adminPassword = (EditText) findViewById(R.id.txtAdminSettingAdminPassword);
		adminConfirmPassword = (EditText) findViewById(R.id.AdminSettingsConfirmPassword);
		email = (EditText) findViewById(R.id.txtAdminSettingUserEmail);
		
		//dialog box for Create Admin
		builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure?")
		.setPositiveButton("Yes", new DialogClickListener())
		.setNegativeButton("No", new DialogClickListener());
		
		//dialog box for remove Member
		remover = new AlertDialog.Builder(this);
		remover.setMessage("Are you sure?")
		.setPositiveButton("Yes", new RemoverClickListener())
	    .setNegativeButton("No", new RemoverClickListener());
		
		//dialog box for unlock member
		unlocker = new AlertDialog.Builder(this);
		unlocker.setMessage("Are you sure?")
		.setPositiveButton("Yes", new UnlockerClickListener())
	    .setNegativeButton("No", new UnlockerClickListener());
		
		//listeners for buttons in admin_settings_view
		findViewById(R.id.btnCreateAdmin)
		.setOnClickListener(new CreateAdminOnClickListener());
		
		findViewById(R.id.btnRemoveMember)
		.setOnClickListener(new RemoveMemberOnClickListner());
		
		findViewById(R.id.btnUnlockAccount)
		.setOnClickListener(new UnlockMemberOnClickListner());

	}
	
	/**
	 * Goes back to the last activity
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	public void onBackPressed() {
	  	startActivity(memberActivity);
	}
	
	/**
	 * Listener for Create Admin button.
	 * Checks three textfields for valid user.
	 * Works exactly the same as register.
	 * 
	 * @author Honey Badger
	 */
	private class CreateAdminOnClickListener implements OnClickListener{
		
		/**
		 * When the user clicks create admin button it gets the three textfields
		 * two to check user email and see if they are identical and the third
		 * is the password for the admin
		 * It creates the admin with these given parameters
		 * 
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 * @param v
		 */
		@Override
		public void onClick(View v) {
			//Strings from textfields
			String email = adminEmail.getText().toString();
			String password = adminPassword.getText().toString();
			String confirmPassword = adminConfirmPassword.getText().toString();
			
			View focusView = null;
			boolean cancel = false;
			
			//Check for a valid password
			if (TextUtils.isEmpty(password)) {
				adminPassword.setError(getString(R.string.error_field_required));
				focusView = adminPassword;
				cancel = true;
			} else if (password.length() < 4) {
				adminPassword.setError(getString(R.string.error_invalid_password));
				focusView = adminPassword;
				cancel = true;
			}
			
			// Check for a valid and non-duplicate email address.
			if (TextUtils.isEmpty(email)){
				adminEmail.setError(getString(R.string.error_field_required));
				focusView = adminEmail;
				cancel = true;
			} else if (!email.contains("@") || !email.contains(".com")) {
				adminEmail.setError(getString(R.string.error_invalid_email));
				focusView = adminEmail;
				cancel = true;
			} else if (Security.contains(email)) {
			// TODO: change error to "already existing email" error once XML updated
				adminEmail.setError(getString(R.string.error_invalid_email));
				focusView = adminEmail;
				cancel = true;
			}
			
			if (cancel) {
				// There was an error; don't attempt login and focus the first
				// form field with an error.
				focusView.requestFocus();
			}
			else {
				//Check if both password fields are the same and
				//then shows a confirmation box
				if(password.equals(confirmPassword)){
					builder.show();
				}
				else{
					adminPassword.setError(getString(R.string.error_no_match));
				}
			}
			
		}

	}
	
	/**
	 * Remove Member Listener is the listener for the Remove Member button.
	 * If the member in the textfield exists then it is removed
	 * otherwise it throws an error
	 * 
	 * @author Honey Badger
	 */
	private class RemoveMemberOnClickListner implements OnClickListener{
		
		/**
		 * When the user clicks on the remove member button
		 * it removes the member from the database.
		 * 
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 * @param arg0
		 */
		@Override
		public void onClick(View arg0) {
			String removeEmail = email.getText().toString();
			if(Security.contains(removeEmail)){
				remover.show();
			}
			else{
				email.setError(getString(R.string.error_invalid_email));
			}
		}
	}
	
	/**
	 * Unlock Member Listener is the listener for the Unlock Member button
	 * If the member in the textfield exists then their failedAttempts is reset to 0
	 * 
	 * @author Honey Badger
	 */
	private class UnlockMemberOnClickListner implements OnClickListener{
		
		/**
		 * When the user clicks unlock is unlocks the member that is entered
		 * into the text field
		 * 
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 * @param v
		 */
		@Override
		public void onClick(View v) {
			String unlockEmail = email.getText().toString();
			if(Security.contains(unlockEmail)){
				unlocker.show();
			}
			else{
				email.setError(getString(R.string.error_invalid_email));
			}
			
		}
	}
	
	/**
	 * Dialog for the Create Admin
	 * 
	 * This just brings up a box asking whether the user wants to create the Admin
	 * if they do then the Admin is created.
	 * 
	 * @author Honey Badger
	 */
	private class DialogClickListener implements DialogInterface.OnClickListener{
		
		/**
		 * If the user clicks yes on the display box, the activity
		 * creates a new member and starts the login activity
		 * If they click no, then the message goes away  and nothing
		 * happens
		 * 
		 * @param dialog
		 * @param which
		 */
		@Override
	    public void onClick(DialogInterface dialog, int which) {
			String email = adminEmail.getText().toString();
			String password = adminPassword.getText().toString();
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	startActivity(memberActivity);
	        	Security.addAdmin(email, password); //register's admin if yes
	        	finish();
	        	break;
	        case DialogInterface.BUTTON_NEGATIVE:
	        	break;
	        default:
	        	break;
	        }
	    }
		
	}
	
	/**
	 * Remove Click Listener is the Dialog for the Remove Member button
	 * Yes or No dialog that asks if the user wants to remove the member from the system
	 * If yes then the member will be removed from the system.
	 * 
	 * @author Honey Badger
	 */
	private class RemoverClickListener implements DialogInterface.OnClickListener{

		/**
		 * Checks if yes or no and then executes remove 
		 * depending on the answer.
		 * Yes - Removes user
		 * No - Cancels
		 * 
		 * @param dialog
		 * @param which
		 */
		@Override
		public void onClick(DialogInterface dialog, int which) {
			String userEmail = email.getText().toString();
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	startActivity(memberActivity);
	        	Security.removeMember(userEmail); //removes member if yes
	        	finish();
	        	break;
	        case DialogInterface.BUTTON_NEGATIVE:
	        	break;
	        default:
	        	break;
	        }
		}
	}
	
	/**
	 * Unlock Click Listener for Unlock Member button
	 * Yes or No Dialog to check that the user wants to unlock the member
	 * If Yes then the user unlocks the member by resetting their failedAttempts to 0.
	 * 
	 * @author Honey Badger
	 */
	private class UnlockerClickListener implements DialogInterface.OnClickListener{
		
		/**
		 * Checks if yes or no and then executes unlock 
		 * depending on the answer.
		 * Yes - Unlocks user
		 * No - Cancels
		 * 
		 * @param dialog
		 * @param which
		 */
		@Override
		public void onClick(DialogInterface dialog, int which) {
			String userEmail = email.getText().toString();
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	startActivity(memberActivity);
	        	Security.unlockMember(userEmail); //unlocks member if yes
	        	finish();
	        	break;
	        case DialogInterface.BUTTON_NEGATIVE:
	        	break;
	        default:
	        	break;
	        }
		}
	}

}
