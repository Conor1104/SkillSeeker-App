
package com.zybooks.skillseekerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SSDataBaseHelper extends SQLiteOpenHelper {

    private static final String SKILLSEEKER_DATABASE = "Contacts";
    private static final int DATABASE_VERSION= 1;
    private static final String TABLE_NAME= "Contacts";

    //Columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NUM = "phone_num";


    public SSDataBaseHelper(@Nullable Context context) {
        super(context, SKILLSEEKER_DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLE contacts ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_num TEXT)
        String createTable = ("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + KEY_NAME + " TEXT, " + KEY_PHONE_NUM + " TEXT)");

        //Note for self- getReadableDatabase() for grabing data. getWritableDatabase for inserting, updating, or delete data
        db.execSQL(createTable);
        //SQLiteDatabase database = this.getWritableDatabase();

        //query to insert

    }
    @Override
    //updates table
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String name, String phone_num){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PHONE_NUM, phone_num);


        db.insert(TABLE_NAME, null, values);
    }
}
