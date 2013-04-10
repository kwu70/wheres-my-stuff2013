package com.honeybadger.wheresmystuff.support;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class is used to run the database for members
 * 
 * @author TheHoneyBadger
 * @version 1
 */
public class DatabaseHandlerMembers extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 8;

	// Database Name
	private static final String DATABASE_NAME = "Data Manager Members";

	// table names
	private static final String TABLE_MEMBERS = "members";
	
	// Table Member Column names
	private static final String KEY_IDM = "memberID";
	
	private static final String KEY_EMAIL = "email";
	
	private static final String KEY_PSWD = "password";
	
	private static final String KEY_MNAME = "membername";
	
	private static final String KEY_FAIL = "failed";
	
	/**
	 * Used to create a table
	 * @param context - View that is creating the database
	 */
	public DatabaseHandlerMembers(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Used to create database
	 * @param db - database to be created
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		//creating tables
		String CREATE_MEMBERS_TABLE = "CREATE TABLE " + TABLE_MEMBERS + "("
				+ KEY_IDM + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT,"
				+ KEY_PSWD + " TEXT, " + KEY_MNAME + " TEXT," + KEY_FAIL + " TEXT" + ")";
		db.execSQL(CREATE_MEMBERS_TABLE);
	}

	/**
	 * This is called if the db version is newer than the current version
	 * 
	 * @param db - database to be upgraded
	 * @param oldVersion - old version # of database
	 * @param newVersion - new version # of database
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Adds a member to the list
	 * @param mem - member to be added
	 */
	protected void addMember(Member mem) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
				
		//if member is an Admin we make the name "Admin" so it is easy to check if the
		//user is an admin when pulling from the DB.
		if(mem instanceof Admin){
			values.put(KEY_IDM, mem.getID());
			values.put(KEY_EMAIL, mem.getEmail());
			values.put(KEY_PSWD, mem.getPassword());
			values.put(KEY_MNAME, "Admin");
			values.put(KEY_FAIL, mem.getFailedAttempts());
		}
		else{
			values.put(KEY_IDM, mem.getID());
			values.put(KEY_EMAIL, mem.getEmail());
			values.put(KEY_PSWD, mem.getPassword());
			values.put(KEY_MNAME, mem.getName());
			values.put(KEY_FAIL, mem.getFailedAttempts());
		}

		// Inserting Row
		db.insert(TABLE_MEMBERS, null, values);
		db.close(); // Closing database connection
	}

	/**
	 * @param id - id of member to retrieve
	 * @return member that was retrieved using the ID
	 */
	protected Member getMember(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		//starts cursor at beginning of member table
		Cursor cursor = db.query(TABLE_MEMBERS, new String[] { KEY_IDM,
				KEY_EMAIL, KEY_PSWD, KEY_MNAME, KEY_FAIL }, KEY_IDM + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		
		//if it is not null make it move to the first position of the table
		if (cursor != null){
			cursor.moveToFirst();

			//if the user is an admin, then creates a new admin
			if((cursor.getString(3).equals("Admin"))){
				try{
					Admin admin = new Admin(Integer.parseInt(cursor.getString(0)),
							cursor.getString(1), cursor.getString(2),
							cursor.getString(3));
					admin.setFailedAttempts(Integer.parseInt(cursor.getString(4)));
					cursor.close();
					db.close();
					return admin;
				}
				catch(Exception e){
					System.out.println("Admin failed to be added " +
							"because the cursor reached a null");
				}
			}
			else{
				try{
					Member mem = new Member(Integer.parseInt(cursor.getString(0)),
							cursor.getString(1), cursor.getString(2), 
							cursor.getString(3));
					mem.setFailedAttempts(Integer.parseInt(cursor.getString(4)));
					cursor.close();
					db.close();
					return mem;
				}
				catch(Exception e){
					System.out.println("Member failed to be added " +
							"because the cursor reached a null");
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return members All members in the database
	 */
	protected ArrayList<Member> getAllMembers(){
		ArrayList<Member> members = new ArrayList<Member>();
		
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_MEMBERS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				if((cursor.getString(3).equals("Admin"))){
					Admin admin = null;
					try{
						 admin = new Admin(Integer.parseInt(cursor.getString(0)),
								cursor.getString(1), cursor.getString(2), 
								cursor.getString(3));
							admin.setFailedAttempts(Integer.parseInt(cursor.getString(4)));
					}
					catch(Exception e){
						System.out.println("Admin failed to be added " +
								"because the cursor reached a null");
					}
					members.add(admin);
				}
				else{
					Member mem = null;
					try{
						mem = new Member(Integer.parseInt(cursor.getString(0)),
								cursor.getString(1), cursor.getString(2), 
								cursor.getString(3));
						mem.setFailedAttempts(Integer.parseInt(cursor.getString(4)));

					}
					catch(Exception e){
						System.out.println("Member failed to be added " +
								"because the cursor reached a null");
					}
					members.add(mem);
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return members;
	}

	/**
	 * 
	 * @return ID to be used for next member that is added
	 */
	protected int getCurrentMemberID(){
		ArrayList<Member> members = getAllMembers();
		return members.size() + 1;
	}
	
	/**
	 * Logs all members into the log
	 */
	public void logMembers(){
		Log.d("DatabaseHandler: ", "Inside Log Members()");
		ArrayList<Member> members = getAllMembers();
		for(Member m: members){
			Log.d("DatabaseHandler: ", m.toString());
		}

	}

	/**
	 * 
	 * @param mem - member to be updated
	 * @return updates the member and returns 1 if it works
	 */
	protected int updateMember(Member mem) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		if(mem instanceof Admin){
			values.put(KEY_IDM, mem.getID());
			values.put(KEY_EMAIL, mem.getEmail());
			values.put(KEY_PSWD, mem.getPassword());
			values.put(KEY_MNAME, "Admin");
			values.put(KEY_FAIL, mem.getFailedAttempts());
		}
		else{
			values.put(KEY_IDM, mem.getID());
			values.put(KEY_EMAIL, mem.getEmail());
			values.put(KEY_PSWD, mem.getPassword());
			values.put(KEY_MNAME, mem.getName());
			values.put(KEY_FAIL, mem.getFailedAttempts());
		}
		
		// updating row
		return db.update(TABLE_MEMBERS, values, KEY_IDM + " = ?",
				new String[] { String.valueOf(mem.getID()) });
	}

	/**
	 * Deletes the specified member
	 * 
	 * @param mem - member to be deleted
	 */
	protected void deleteMember(Member mem) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_MEMBERS, KEY_IDM + " = ?",
				new String[] { String.valueOf(mem.getID()) });
		db.close();
	}

	/**
	 * 
	 * @return the number of members in the database
	 */
	protected int getMemberscount() {
		String countQuery = "SELECT  * FROM " + TABLE_MEMBERS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
}