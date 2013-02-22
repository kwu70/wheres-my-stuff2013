package com.honeybadger.wheresmystuff.views;

import com.honeybadger.wheresmystuff.R;
import com.honeybadger.wheresmystuff.support.Login;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends Activity{
	
	private String email;
	private String password;
	private String confirmPassword;
	
	private AlertDialog.Builder builder;
	
	private EditText eEmail;
	private EditText ePassword;
	private EditText eConfirmPassword;
	
	private Intent i;
	
	private Login lg;
		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_view);
		
	    i = new Intent(this, LoginView.class);
	    
	    lg = new Login();
	    
	    builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure?").setPositiveButton("Yes", new DialogClickListener())
	    .setNegativeButton("No", new DialogClickListener());

		
		eEmail = (EditText) findViewById(R.id.txtEmail);
		
		ePassword = (EditText) findViewById(R.id.txtPassword);
		eConfirmPassword = (EditText) findViewById(R.id.txtConfirm);
		
		findViewById(R.id.btnRegister).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						email = eEmail.getText().toString();
						password = ePassword.getText().toString();
						confirmPassword = eConfirmPassword.getText().toString();
						
						if(password.equals(confirmPassword)){
							builder.show();
						}
						else{
							ePassword.setError(getString(R.string.error_no_match));
						}
					}
				});
		
	}
	
	public void onBackPressed() {
	    startActivity(i);
	}
	
	private class DialogClickListener implements DialogInterface.OnClickListener{

		@Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	Login.createAccount(email, password);
	        	startActivity(i);
	        	finish();
	        	break;
	        case DialogInterface.BUTTON_NEGATIVE:
	        	break;
	        }
	    }
		
	}
}
