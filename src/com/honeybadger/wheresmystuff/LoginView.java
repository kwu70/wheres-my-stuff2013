package com.honeybadger.wheresmystuff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class LoginView extends Activity{
	String email;
	String password;
	
	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
	
	//Intent to switch to next activity
	private Intent i;
	
	//Login instance
	private Login lg;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);		
		i = new Intent(this, MemberActivity.class);
		lg = new Login();
		
		email = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(email);
		
		mPasswordView = (EditText) findViewById(R.id.password);
		
		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}
	private void attemptLogin() {
		email = mEmailView.getText().toString();
		password = mPasswordView.getText().toString();
		
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
		} else {
			if(lg.validate(email, password)){
				startActivity(i);
				finish();
			}
			else{
				mEmailView.setError(getString(R.string.error_incorrect_password));
			}
		}
	}
}
