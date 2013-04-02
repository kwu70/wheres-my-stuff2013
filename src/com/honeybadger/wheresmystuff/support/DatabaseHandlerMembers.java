package com.honeybadger.wheresmystuff.support;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandlerMembers extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 5;

	// Database Name
	private static final String DATABASE_NAME = "Data Manager Members";

	// table names
	private static final String TABLE_MEMBERS = "members";
	
	// Table Member Column names
	private static final String KEY_IDM = "memberID";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PSWD = "password";
	private static final String KEY_MNAME = "membername";


	public DatabaseHandlerMembers(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		//creating tables
		String CREATE_MEMBERS_TABLE = "CREATE TABLE " + TABLE_MEMBERS + "("
				+ KEY_IDM + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT,"
				+ KEY_PSWD + " TEXT, " + KEY_MNAME + " TEXT" + ")";
		db.execSQL(CREATE_MEMBERS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new member
	public void addMember(Member mem) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		
		//if member is an Admin we make the name "Admin" so it is easy to check if the
		//user is an admin when pulling from the DB.
		if(mem instanceof Admin){
			values.put(KEY_IDM, mem.getID());
			values.put(KEY_EMAIL, mem.getEmail());
			values.put(KEY_PSWD, mem.getPassword());
			values.put(KEY_MNAME, "Admin");	
		}
		else{
			values.put(KEY_IDM, mem.getID());
			values.put(KEY_EMAIL, mem.getEmail());
			values.put(KEY_PSWD, mem.getPassword());
			values.put(KEY_MNAME, mem.getName());
		}

		// Inserting Row
		db.insert(TABLE_MEMBERS, null, values);
		db.close(); // Closing database connection
	}


	// Getting single member
	Member getMember(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		//starts cursor at beginning of member table
		Cursor cursor = db.query(TABLE_MEMBERS, new String[] { KEY_IDM,
				KEY_EMAIL, KEY_PSWD, KEY_MNAME }, KEY_IDM + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		
		//if it is not null make it move to the first position of the table
		if (cursor != null)
			cursor.moveToFirst();
		
		//if the user is an admin, then creates a new admin
		if((cursor.getString(3).equals("Admin"))){
			
			Admin admin = new Admin(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1), cursor.getString(2), cursor.getString(3));			
			cursor.close();
			db.close();
			return admin;
		}
		else{
			Member mem = new Member(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1), cursor.getString(2), cursor.getString(3));
			cursor.close();
			db.close();
			return mem;
		}
	}
	
	
	public ArrayList<Member> getAllMembers(){
		ArrayList<Member> members = new ArrayList<Member>();
		
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_MEMBERS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				if((cursor.getString(3).equals("Admin"))){
					Admin admin = new Admin(Integer.parseInt(cursor.getString(0)),
							cursor.getString(1), cursor.getString(2), cursor.getString(3));
					members.add(admin);
				}
				else{
					Member mem = new Member(Integer.parseInt(cursor.getString(0)),
							cursor.getString(1), cursor.getString(2), cursor.getString(3));
					members.add(mem);
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return members;
	}

	//We need to have a unique id for each place. Since a database is persistent, we need to find where to start the id
	//This method should be called in onCreate of the ListView fragment
	public int getCurrentMemberID()
	{
		ArrayList<Member> members = (ArrayList<Member>) getAllMembers();
		return members.size() +1;
	}

	
	void logMembers()
	{
		Log.d("DatabaseHandler: ", "Inside Log Members()");
		ArrayList<Member> members = (ArrayList<Member>) getAllMembers();
		for(Member m: members)
		{
			Log.d("DatabaseHandler: ", m.toString());
		}

	}

	// Updating single member
	public int updateMember(Member mem) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		if(mem instanceof Admin){
			values.put(KEY_IDM, mem.getID());
			values.put(KEY_EMAIL, mem.getEmail());
			values.put(KEY_PSWD, mem.getPassword());
			values.put(KEY_MNAME, "Admin");
		}
		else{
			values.put(KEY_IDM, mem.getID());
			values.put(KEY_EMAIL, mem.getEmail());
			values.put(KEY_PSWD, mem.getPassword());
			values.put(KEY_MNAME, mem.getName());
		}
		
		// updating row
		return db.update(TABLE_MEMBERS, values, KEY_IDM + " = ?",
				new String[] { String.valueOf(mem.getID()) });
	}

	// Deleting single place
	public void deleteMember(Member mem) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_MEMBERS, KEY_IDM + " = ?",
				new String[] { String.valueOf(mem.getID()) });
		db.close();
	}

	// Getting member Count
	public int getMemberscount() {
		String countQuery = "SELECT  * FROM " + TABLE_MEMBERS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
}