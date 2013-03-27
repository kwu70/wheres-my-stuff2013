package com.honeybadger.wheresmystuff.support;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "Data Manager";

	// table names
	private static final String TABLE_ITEMS = "items";
	private static final String TABLE_MEMBERS = "members";

	// Table Item Columns names
	private static final String KEY_IDI = "itemID";
	private static final String KEY_INAME = "name";
	private static final String KEY_DESC = "description";
	private static final String KEY_MEMID = "memberid";
	private static final String KEY_STATUS = "itemstatus";
	private static final String KEY_RESOL = "resolved";
	private static final String KEY_TYPE = "type";
	private static final String KEY_MONTH = "month";
	private static final String KEY_DAY = "day";
	private static final String KEY_YEAR = "year";
	
	// Table Member Column names
	private static final String KEY_IDM = "memberID";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PSWD = "password";
	private static final String KEY_MNAME = "membername";


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
				+ KEY_IDI + " INTEGER PRIMARY KEY," + KEY_INAME + " TEXT,"
				+ KEY_DESC + " TEXT, " + KEY_MEMID+ " TEXT" + KEY_STATUS 
				+ " TEXT" + KEY_RESOL + " TEXT" + KEY_TYPE + " TEXT" + KEY_MONTH
				+ " TEXT" + KEY_DAY + " TEXT" + KEY_YEAR + " TEXT" + ")";
		String CREATE_MEMBERS_TABLE = "CREATE TABLE " + TABLE_MEMBERS + "("
				+ KEY_IDM + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT,"
				+ KEY_PSWD + " TEXT, " + KEY_MNAME + " TEXT" + ")";
		db.execSQL(CREATE_ITEMS_TABLE);
		db.execSQL(CREATE_MEMBERS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new place
	void addMember(Member mem) {
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

		// Inserting Row
		db.insert(TABLE_MEMBERS, null, values);
		db.close(); // Closing database connection
	}
	
	// Adding new place
	void addItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_IDI, item.getID());
		values.put(KEY_INAME, item.getName());
		values.put(KEY_DESC, item.getDescription());
		values.put(KEY_MEMID, item.getOwner().getID());
		values.put(KEY_STATUS, item.getStatus());
		values.put(KEY_RESOL, item.getResolved());
		values.put(KEY_TYPE, item.getType());
		values.put(KEY_MONTH, item.getMonth());
		values.put(KEY_DAY, item.getDay());
		values.put(KEY_YEAR, item.getYear());


		// Inserting Row
		db.insert(TABLE_ITEMS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single place
	Member getMember(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_MEMBERS, new String[] { KEY_IDM,
				KEY_EMAIL, KEY_PSWD, KEY_MNAME }, KEY_IDM + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		if((cursor.getString(3).equals("Admin"))){
			Admin admin = new Admin(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1), cursor.getString(2), cursor.getString(3));
			return admin;
		}
		else{
			Member mem = new Member(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1), cursor.getString(2), cursor.getString(3));
			return mem;
		}
	}
	
	Item getItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_MEMBERS, new String[] { KEY_IDI,
				KEY_INAME, KEY_DESC, KEY_MEMID, KEY_STATUS, KEY_RESOL,
				KEY_TYPE, KEY_MONTH, KEY_DAY, KEY_YEAR }, KEY_IDI + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		int memID = Integer.parseInt(cursor.getString(3));
		ArrayList<Member>  mem = getAllMembers();
		Member temp = null;
		for(Member m : mem){
			if(m.getID() == memID){
				temp = m;
			}
		}
		
		Item item = new Item(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), temp, Boolean.getBoolean(cursor.getString(4)), 
				Boolean.getBoolean(cursor.getString(5)), cursor.getString(6), Integer.parseInt(cursor.getString(7)), 
				Integer.parseInt(cursor.getString(8)), Integer.parseInt(cursor.getString(9)));
		return item;
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
		
		return members;
	}
	
	
	public ArrayList<Item> getAllItems(){
		ArrayList<Item> items = new ArrayList<Item>();
		
		String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				int memID = Integer.parseInt(cursor.getString(3));
				ArrayList<Member>  mem = getAllMembers();
				Member temp = null;
				for(Member m : mem){
					if(m.getID() == memID){
						temp = m;
					}
				}
				
				Item item = new Item(Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), cursor.getString(2), temp, Boolean.getBoolean(cursor.getString(4)), 
						Boolean.getBoolean(cursor.getString(5)), cursor.getString(6), Integer.parseInt(cursor.getString(7)), 
						Integer.parseInt(cursor.getString(8)), Integer.parseInt(cursor.getString(9)));
				// Adding item to list
				items.add(item);
			} while (cursor.moveToNext());
		}
		
		return items;
	}

	//We need to have a unique id for each place. Since a database is persistent, we need to find where to start the id
	//This method should be called in onCreate of the ListView fragment
	public int getCurrentMemberID()
	{
		ArrayList<Member> members = (ArrayList<Member>) getAllMembers();
		return members.size() +1;
	}

	public int getCurrentItemID()
	{
		ArrayList<Item> items = (ArrayList<Item>) getAllItems();
		return items.size() +1;
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
	
	void logItems()
	{
		Log.d("DatabaseHandler: ", "Inside Log Items()");
		ArrayList<Item> items = (ArrayList<Item>) getAllItems();
		for(Item i: items)
		{
			Log.d("DatabaseHandler: ", i.toString());
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
	
	// Updating single item
	public int updateItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();
		

		ContentValues values = new ContentValues();
		values.put(KEY_IDI, item.getID());
		values.put(KEY_INAME, item.getName());
		values.put(KEY_DESC, item.getDescription());
		values.put(KEY_MEMID, item.getOwner().getID());
		values.put(KEY_STATUS, item.getStatus());
		values.put(KEY_RESOL, item.getResolved());
		values.put(KEY_TYPE, item.getType());
		values.put(KEY_MONTH, item.getMonth());
		values.put(KEY_DAY, item.getDay());
		values.put(KEY_YEAR, item.getYear());

		// updating row
		return db.update(TABLE_ITEMS, values, KEY_IDI + " = ?",
				new String[] { String.valueOf(item.getID()) });
	}

	// Deleting single place
	public void deleteMember(Member mem) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_MEMBERS, KEY_IDM + " = ?",
				new String[] { String.valueOf(mem.getID()) });
		db.close();
	}
	
	// Deleting single place
	public void deleteItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ITEMS, KEY_IDI + " = ?",
				new String[] { String.valueOf(item.getID()) });
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
	
	// Getting member Count
	public int getItemsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_ITEMS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
}