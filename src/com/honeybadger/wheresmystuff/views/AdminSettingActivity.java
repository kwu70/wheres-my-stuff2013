package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.Member;
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

public class AdminSettingActivity extends Activity{
	
	private String userEmail;
	private Member currentMember;
	
	private Intent memberActivity;
	
	private EditText adminEmail;
	private EditText adminPassword;
	private EditText adminConfirmPassword;
	private EditText email;
	
	private AlertDialog.Builder builder;
	private AlertDialog.Builder remover;
	private AlertDialog.Builder unlocker;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_settings_view);
		
		Intent intent = getIntent();
		userEmail = intent.getExtras().getString("userEmail");
		currentMember = Security.getMember(userEmail);
		
		memberActivity = new Intent(this, MemberActivity.class);
		memberActivity.putExtra("userEmail", userEmail);
		
		adminEmail = (EditText) findViewById(R.id.txtAdminSettingAdminEmail);
		adminPassword = (EditText) findViewById(R.id.txtAdminSettingAdminPassword);
		adminConfirmPassword = (EditText) findViewById(R.id.AdminSettingsConfirmPassword);
		email = (EditText) findViewById(R.id.txtAdminSettingUserEmail);
		
		builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure?").setPositiveButton("Yes", new DialogClickListener())
	    .setNegativeButton("No", new DialogClickListener());
		
		remover = new AlertDialog.Builder(this);
		remover.setMessage("Are you sure?").setPositiveButton("Yes", new RemoverClickListener())
	    .setNegativeButton("No", new RemoverClickListener());
		
		unlocker = new AlertDialog.Builder(this);
		unlocker.setMessage("Are you sure?").setPositiveButton("Yes", new UnlockerClickListener())
	    .setNegativeButton("No", new UnlockerClickListener());
		
		findViewById(R.id.btnCreateAdmin).setOnClickListener(new CreateAdminOnClickListener());
		findViewById(R.id.btnRemoveMember).setOnClickListener(new RemoveMemberOnClickListner());
		findViewById(R.id.btnUnlockAccount).setOnClickListener(new UnlockMemberOnClickListner());

		
	}
	
	public void onBackPressed() {
	  	startActivity(memberActivity);
	}
	
	//CREATE ADMIN
	private class CreateAdminOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
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
			if (TextUtils.isEmpty(email)) {						
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
	
	//REMOVE MEMBER
	private class RemoveMemberOnClickListner implements OnClickListener{
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
	
	//UNLOCK MEMBER
	private class UnlockMemberOnClickListner implements OnClickListener{
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
	
	private class DialogClickListener implements DialogInterface.OnClickListener{

		/*
		 * If the user clicks yes on the display box, the activity
		 * creates a new member and starts the login activity
		 * If they click no, then the message goes away  and nothing
		 * happens
		 */
		@Override
	    public void onClick(DialogInterface dialog, int which) {
			String email = adminEmail.getText().toString();
			String password = adminPassword.getText().toString();
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	startActivity(memberActivity);
	        	Security.addAdmin(email, password); //(register's new admin)
	        	finish();
	        	break;
	        case DialogInterface.BUTTON_NEGATIVE:
	        	break;
	        }
	    }
		
	}
	
	private class RemoverClickListener implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			String userEmail = email.getText().toString();
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	startActivity(memberActivity);
	        	Security.removeMember(userEmail);
	        	finish();
	        	break;
	        case DialogInterface.BUTTON_NEGATIVE:
	        	break;
	        }
		}
	}
	
	private class UnlockerClickListener implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			String userEmail = email.getText().toString();
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	startActivity(memberActivity);
	        	Security.unlockMember(userEmail);
	        	finish();
	        	break;
	        case DialogInterface.BUTTON_NEGATIVE:
	        	break;
	        }
			
		}
		
	}

}
