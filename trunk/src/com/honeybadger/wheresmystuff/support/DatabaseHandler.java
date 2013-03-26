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
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "Data Manager";

	// places table name
	private static final String TABLE_ITEMS = "items";
	private static final String TABLE_MEMBERS = "members";

	// places Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	//private static final String KEY_PH_NO = "phone_number";
	private static final String KEY_LAT = "latitude";
	private static final String KEY_LON = "longitude";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_LAT + " TEXT, " + KEY_LON + " TEXT" + ")";
		String CREATE_MEMBERS_TABLE = "CREATE TABLE " + TABLE_MEMBERS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_LAT + " TEXT, " + KEY_LON + " TEXT" + ")";
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
		values.put(KEY_ID, mem.getID()); //place id
		values.put(KEY_NAME, mem.getName()); // place Name
		values.put(KEY_LAT, mem.getLat()); // place latitude
		values.put(KEY_LON, mem.getLon()); //place longitude

		// Inserting Row
		db.insert(TABLE_MEMBERS, null, values);
		db.close(); // Closing database connection
	}
	
	// Adding new place
	void addItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, item.getID()); //place id
		values.put(KEY_NAME, item.getName()); // place Name
		values.put(KEY_LAT, item.getLat()); // place latitude
		values.put(KEY_LON, item.getLon()); //place longitude

		// Inserting Row
		db.insert(TABLE_ITEMS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single place
	Member getMember(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_MEMBERS, new String[] { KEY_ID,
				KEY_NAME, KEY_LAT, KEY_LON }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Member mem = new Member(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)));
		// return place
		return mem;
	}
	
	Item getItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_MEMBERS, new String[] { KEY_ID,
				KEY_NAME, KEY_LAT, KEY_LON }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Item item = new Item(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)));
		// return place
		return item;
	}
	
	public List<Member> getAllMembers(){
		List<Member> members = new ArrayList<Member>();
		
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_MEMBERS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Member member = new Member(Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)));
				// Adding member to list
				members.add(member);
			} while (cursor.moveToNext());
		}
		
		return members;
	}
	
	
	public List<Item> getAllItems(){
		List<Item> items = new ArrayList<Item>();
		
		String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Item item = new Item(Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)));
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

	// Updating single place
	public int updateMember(Member mem) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, mem.getName());
		values.put(KEY_LAT, mem.getLat());
		values.put(KEY_LON, mem.getLon());

		// updating row
		return db.update(TABLE_MEMBERS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(mem.getID()) });
	}
	
	// Updating single place
	public int updateItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, item.getName());
		values.put(KEY_LAT, item.getLat());
		values.put(KEY_LON, item.getLon());

		// updating row
		return db.update(TABLE_MEMBERS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(item.getID()) });
	}

	// Deleting single place
	public void deleteMember(Member mem) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_MEMBERS, KEY_ID + " = ?",
				new String[] { String.valueOf(mem.getID()) });
		db.close();
	}
	
	// Deleting single place
	public void deleteItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_MEMBERS, KEY_ID + " = ?",
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