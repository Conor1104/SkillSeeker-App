
package com.zybooks.skillseekerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SSDataBaseHelper extends SQLiteOpenHelper {

    private static final String SKILLSEEKER_DATABASE = "Contacts";
    private static final int DATABASE_VERSION= 1;
    private static final String TABLE_NAME= "Contacts";

    private static final String FREELANCER_TABLE = "Freelancers";


    //Columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NUM = "phone_num";
    private static final String AGE = "age";



    //Freelancer Table
    /*
    String TIME_IN_BUSINESS = "FreelancerExperience";
    Integer REVIEWS = null;
    Double AVGCOST = 0.0;
    String PHONE_NUM = "phone_num";
     */
    private static final String[] FREELANC_ATTRI = {"FREELANCER_TABLE","FRE_ID","FRE_EXP", "REVIEWS", "AVG_COST","PHONE_NUM"};
    //Freelancer Experience should be integer? or just a string?
    //Reviews probably like stars, how many stars they have, could be double
    public SSDataBaseHelper(@Nullable Context context) {
        super(context, SKILLSEEKER_DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLE contacts ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_num TEXT, AGE)
        String createTable = ("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + KEY_NAME + " TEXT, " + KEY_PHONE_NUM + " TEXT, " + AGE+ " TEXT)");

        String createFreeTable = ("CREATE TABLE " + FREELANC_ATTRI[0] + " ("
                + FREELANC_ATTRI[1] + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FREELANC_ATTRI[2] + " TEXT, "
                + FREELANC_ATTRI[3] + " REAL, "
                + FREELANC_ATTRI[4] + " REAL, "
                + FREELANC_ATTRI[5] + " TEXT)");


        //Note for self- getReadableDatabase() for grabbing data. getWritableDatabase for inserting, updating, or delete data
        db.execSQL(createTable);

        //Created this table, should work, just lazy and put it into an array
        db.execSQL(createFreeTable);
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
