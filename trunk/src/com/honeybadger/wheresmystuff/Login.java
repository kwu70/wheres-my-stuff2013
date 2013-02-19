package com.honeybadger.wheresmystuff;

public class Login {
	private Security sc;
	public Login(){
		sc = new Security();
	}
	public boolean lockOut(){
		return false;
	}
	public boolean validate(String email, String psswd){
		int emailIndex = 0;
		for(int i =0; i < sc.emails.size(); i++){
			if(email.equals(sc.emails.get(i))){
				emailIndex = i;
			}
			else{
				return createAccount(email, psswd);
			}
		}
		if(psswd.equals(sc.passwords.get(emailIndex))){
			return true;
		}
		return false;
	}
	private boolean createAccount(String email, String psswd) {
		sc.emails.add(email);
		sc.passwords.add(psswd);
		return true;
	}
}
