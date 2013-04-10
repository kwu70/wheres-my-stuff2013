package com.honeybadger.wheresmystuff.support;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class is used to manage the database for items
 * 
 * @author - TheHoneyBadgers
 * @version - 1
 */

public class DatabaseHandlerItems extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 16;

	// Database Name
	private static final String DATABASE_NAME = "Data Manager Items";

	// table names
	private static final String TABLE_ITEMS = "items";
	
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
	
	private static final String KEY_LOC = "location";

	/**
	 * Used to create a table
	 * @param context - View that is creating the database
	 */
	public DatabaseHandlerItems(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Used to create a database
	 * @param db - database to be created
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		//creating tables
		final String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
				+ KEY_IDI + " INTEGER PRIMARY KEY," + KEY_INAME + " TEXT,"
				+ KEY_DESC + " TEXT, " + KEY_MEMID  + " TEXT," + KEY_STATUS 
				+ " TEXT," + KEY_RESOL + " TEXT," + KEY_TYPE + " TEXT," + KEY_MONTH
				+ " TEXT," + KEY_DAY + " TEXT," + KEY_YEAR + " TEXT," + KEY_LOC 
				+ " TEXT" + ")";
		db.execSQL(CREATE_ITEMS_TABLE);
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Takes all item values and stores them into the database
	 * @param item - item being added to the database
	 */
	protected void addItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_IDI, item.getID());
		values.put(KEY_INAME, item.getName());
		values.put(KEY_DESC, item.getDescription());
		values.put(KEY_MEMID, item.getOwner().getID());
		//Adding appropriate Values
		if(!item.getStatus()){
			values.put(KEY_STATUS, "false");
		}
		else{
			values.put(KEY_STATUS, "true");
		}
		if(!item.getResolved()){
			values.put(KEY_RESOL, "false");
		}
		else{
			values.put(KEY_RESOL, "true");
		}
		values.put(KEY_TYPE, item.getType());
		values.put(KEY_MONTH, item.getMonth());
		values.put(KEY_DAY, item.getDay());
		values.put(KEY_YEAR, item.getYear());
		values.put(KEY_LOC, item.getLocation());

		// Inserting Row
		db.insert(TABLE_ITEMS, null, values);
		db.close(); // Closing database connection
	}
	
	/**
	 * @param id - Item that is being retrieved from the database
	 * @return item Item that was found from the inputted id
	 */
	protected Item getItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_ITEMS, new String[] { KEY_IDI,
				KEY_INAME, KEY_DESC, KEY_MEMID, KEY_STATUS, KEY_RESOL,
				KEY_TYPE, KEY_MONTH, KEY_DAY, KEY_YEAR, KEY_LOC }, KEY_IDI + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		int memID = 0;
		if (cursor != null){
			cursor.moveToFirst();
			try{
				memID = Integer.parseInt(cursor.getString(3));
			}
			catch(Exception e){
				System.out.println("No member ID was inputted");
				System.out.println(e.getStackTrace());
			}
			ArrayList<Member>  mem = Security.getDBM().getAllMembers();
			Member temp = null;
			for(Member m : mem){
				if(m.getID() == memID){
					temp = m;
				}
			}
			Boolean temp1;
			Boolean temp2;
			if(cursor.getString(4).equals("false")){
				temp1 = false;
			}
			else{
				temp1 = true;
			}
			if(cursor.getString(5).equals("false")){
				temp2 = false;
			}
			else{
				temp2 = true;
			}
			Item item = null;
			try{
				item = new Item(Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), cursor.getString(2), temp, temp1, 
						temp2, cursor.getString(6), 
						Integer.parseInt(cursor.getString(7)), 
						Integer.parseInt(cursor.getString(8)), 
						Integer.parseInt(cursor.getString(9)), cursor.getString(10));
			}
			catch(Exception e){
				System.out.println("No member ID was inputted");
				System.out.println(e.getStackTrace());
			}
			
			cursor.close();
			db.close();
			return item;
		}
		return null;
	}
	
	/**
	 * @return items - All items in the database
	 */
	protected ArrayList<Item> getAllItems(){
		ArrayList<Item> items = new ArrayList<Item>();
		
		String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				int memID = 0;
				try{
					memID = Integer.parseInt(cursor.getString(3));
				}
				catch(Exception e){
					System.out.println("No member ID was inputted");
					System.out.println(e.getStackTrace());
				}
				ArrayList<Member>  mem = Security.getDBM().getAllMembers();
				Member temp = null;
				for(Member m : mem){
					if(m.getID() == memID){
						temp = m;
					}
				}
				Boolean temp1;
				Boolean temp2;
				if(cursor.getString(4).equals("false")){
					temp1 = false;
				}
				else{
					temp1 = true;
				}
				if(cursor.getString(5).equals("false")){
					temp2 = false;
				}
				else{
					temp2 = true;
				}
								
				Item item = null;
				try{
					item = new Item(Integer.parseInt(cursor.getString(0)),
							cursor.getString(1), cursor.getString(2), temp, temp1, 
							temp2, cursor.getString(6), 
							Integer.parseInt(cursor.getString(7)),
							Integer.parseInt(cursor.getString(8)), 
							Integer.parseInt(cursor.getString(9)), cursor.getString(10));
				}
				catch(Exception e){
					System.out.println("No member ID was inputted");
					System.out.println(e.getStackTrace());
				}
				
				// Adding item to list
				items.add(item);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return items;
	}

	/**
	 * @return int - the current id to be assigned to the next added
	 * item
	 */
	protected int getCurrentItemID(){
		ArrayList<Item> items = getAllItems();
		return items.size() + 1;
	}
	
	/**
	 * Logs all the items into the log
	 */
	protected void logItems(){
		Log.d("DatabaseHandler: ", "Inside Log Items()");
		ArrayList<Item> items = getAllItems();
		for(Item i: items){
			Log.d("DatabaseHandler: ", i.toString());
		}

	}
	
	/**
	 * @param item - item that is being updated
	 * @return int - 1 if database succesfully updated
	 */
	protected int updateItem(Item item) {
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
		values.put(KEY_LOC, item.getLocation());
		
		// updating row
		return db.update(TABLE_ITEMS, values, KEY_IDI + " = ?",
				new String[] { String.valueOf(item.getID()) });
	}
	
	/**
	 * Deletes an item from the database
	 * @param item - item to be deleted
	 */
	protected void deleteItem(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ITEMS, KEY_IDI + " = ?",
				new String[] { String.valueOf(item.getID()) });
		db.close();
	}
	
	/**
	 * 
	 * @return A count of items in the database
	 */
	protected int getItemsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_ITEMS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
}