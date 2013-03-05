package com.honeybadger.wheresmystuff.support;


public class Admin extends Member {

	public Admin(String email, String password) {
		super(email, password);
	}

	public Admin(String email, String password, String name){
		super(email, password, name);
	}
	
	public void removeMember(Member mem){
		Security.removeMember(mem.getEmail());
	}
	
	public void createAdmin(String email, String password){
		Security.addAdmin(email, password);
	}
	
	public void unlockAccount(Member mem){
		Security.resetAttempts(mem);
	}
}
