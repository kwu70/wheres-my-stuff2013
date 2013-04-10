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
 * 
 * @author Honey Badger
 * @version 1.0
 */
public class LoginView extends Activity{

	//Email and password user typed in
	private String email;
	private String password; 
	
	// Email and password Text boxes
	private EditText mEmailView;
	private EditText mPasswordView;
	
	//Used to show the user that email belongs in that text box
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
	
	//Intent to switch MemberActivity
	private Intent memberIntent;
	
	//Intent to switch to RegisterActivity
	private Intent registerIntent;
	
	//Intent to go to the load screen
	private Intent load;
			
	private Login lg;
	
	//length of the password
	private static final int passwordLength = 4;

	/**
	 * Called when the activity is first created.
	 * Creates an Intent object which moves to MemberActivity.class and creates 
	 * a new Login instance.  Sets up Login UI and attempts to login after 
	 * sign-in button is pressed.
	 * 
	 * @param savedInstanceState state of activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		memberIntent = new Intent(this, MemberActivity.class);		
		registerIntent = new Intent(this, RegisterActivity.class);
		load = new Intent(this, Loading.class);
		//startActivity(load);
		
		
		//login initialized so we can access methods and it sets up a user account
		lg = new Login(this);
		
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
						startActivity(registerIntent);
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
		} else if (password.length() < passwordLength) {
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
			if(lg.validate(email, password)){
				memberIntent.putExtra("userEmail", email);
				startActivity(memberIntent);
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
	
	/**
	 * This method is called when the back button is pressed
	 * and returns to home.
	 */
	public void onBackPressed() {
		finish();
	}
}