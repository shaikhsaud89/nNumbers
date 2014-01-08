package com.saud.nnumbers;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PrimesDatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "PrimesManager";
	
	private static final String TABLE_PRIMES = "Primes";

	private static final String KEY_NUMBER = "number_id";
	private static final String KEY_VALUE = "value";
	
	public PrimesDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PRIMES_TABLE = "CREATE TABLE " + TABLE_PRIMES + "("
				+ KEY_NUMBER + " TEXT PRIMARY KEY," + KEY_VALUE + " TEXT" + ")";
		db.execSQL(CREATE_PRIMES_TABLE);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRIMES);
		onCreate(db);
	}
	
	public int getPrimesCount() {
		int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_PRIMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor != null && !cursor.isClosed()){
        	count = cursor.getCount();
            cursor.close();
        }
        return count;
    }
	
	public ArrayList<Item> getAllPrimes() {
		ArrayList<Item> list = new ArrayList<Item>();

		String selectQuery = "SELECT  * FROM " + TABLE_PRIMES;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Item item = new Item();
				item.setNumber(cursor.getString(cursor.getColumnIndex(KEY_NUMBER)));
				item.setValue(cursor.getString(cursor.getColumnIndex(KEY_VALUE)));
				list.add(item);
			} while (cursor.moveToNext());
		}

		db.close();
		return list;
	}
	
    public ArrayList<Item> getPrimesUpto(String N) {
		ArrayList<Item> list = new ArrayList<Item>();
		
		String selectQuery = "SELECT * FROM " + TABLE_PRIMES + " LIMIT " + N;
		
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Item item = new Item();
				item.setNumber(cursor.getString(cursor.getColumnIndex(KEY_NUMBER)));
				item.setValue(cursor.getString(cursor.getColumnIndex(KEY_VALUE)));
				list.add(item);
			} while (cursor.moveToNext());
		}

		db.close();
		return list;	
	}
	
	public int getLastPrime() {
		int lastValue = 0;
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_PRIMES;
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToLast();
		if(cursor != null && !cursor.isClosed()){
			lastValue = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_VALUE)));
            cursor.close();
        }
		return lastValue;
	}
	
	public void addPrime(Item item) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_VALUE, item.getValue());
		values.put(KEY_NUMBER, item.getNumber());

		db.insert(TABLE_PRIMES, null, values);

		db.close();
	}

}
