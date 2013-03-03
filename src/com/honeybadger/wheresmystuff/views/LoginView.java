package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.Login;
import com.honeybadger.wheresmystuff.support.Security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

/**
 * The LoginView class contains the UI screen users are shown before they
 * can access their MemberActivity screen.
 */
public class LoginView extends Activity{
	String email;
	String password; 
	
	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
	
	//Intent to switch to next activity
	private Intent i;
	private Intent i2;
	
	private Login lg;

	/**
	 * Called when the activity is first created.
	 * Creates Intent object which moves to MemberActivity.class and creates 
	 * a new Login instance.  Sets up Login UI and attempts to login after 
	 * sign-in button is pressed.
	 * 
	 * @param state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		i = new Intent(this, MemberActivity.class);		
		i2 = new Intent(this, RegisterActivity.class);
		
		//login initialized so we can access methods and it sets up a user account since
		//we do not have persistent data.
		lg = new Login();
		
		email = getIntent().getStringExtra(EXTRA_EMAIL);
		
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(email);
		
		mPasswordView = (EditText) findViewById(R.id.password);
		
		//if sign in button is clicked, then we attempt to login
		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		
		//if create account is clicked, then we go to the register activity
		findViewById(R.id.btnCreateAccount).setOnClickListener(
				new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						startActivity(i2);
						finish();
					}
				});
	}
	
	/**
	 * Each of the credential fields is checked for appropriate length and
	 * type of characters showing error messages for invalid input.
	 * If the email and password fields are valid, member is logged in and 
	 * taken to the MemberActivity screen.
	 */
	private void attemptLogin() {
		
		//gets email and password from text boxes and puts them in variables.
		email = mEmailView.getText().toString();
		password = mPasswordView.getText().toString();
		
		//used to focus the view on whatever is incorrect or cancel 
		//the login process if the correct parameters aren't met
		boolean cancel = false;
		View focusView = null;
		
		//Check for a valid password
		if (TextUtils.isEmpty(password)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (password.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!email.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}
		
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		}
		else {
			if(lg.validate(email, password) && !lg.lockOut(Security.getMember(email))){
				i.putExtra("userEmail", email);
				startActivity(i);
				finish();
			}
			else if(lg.lockOut(Security.getMember(email))){
				mEmailView.setError(getString(R.string.error_lock_out));
				mPasswordView.setError(null);
			}
			else{
				mPasswordView.setError(getString(R.string.error_incorrect_password_email));
			}
		}
	}
	public void onBackPressed() {
		finish();
	}
}