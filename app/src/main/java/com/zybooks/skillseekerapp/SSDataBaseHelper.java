
package com.zybooks.skillseekerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SSDataBaseHelper extends SQLiteOpenHelper {

    //USER_DATA_TABLE
    private static final String SKILLSEEKER_DATABASE = "Contacts";
    //If trying to add a new data set to the DATABASE_VERSION must be incremented to see new change
    private static final int DATABASE_VERSION= 3;
    private static final String TABLE_NAME= "Contacts";

    //Columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NUM = "phone_num";
    private static final String KEY_USER_AGE = "age";
    //USER_DATA_TABLE

    //FREELANCERS_DATA_TABEL
    private static final String FREELANCER_TABLE_NAME= "freelancer_contacts";
    private static final String FREELANCER_ID= "freelancer_id";
    private static final String FREELANCER_NAME = "freelancer_name";
    private static final String FREELANCER_EXP= "freelancer_exp";
    private static final String FREELANCER_STAR_REVIEW= "review_stars";


    //FREELANCERS_DATA_TABEL


    public SSDataBaseHelper(@Nullable Context context) {
        super(context, SKILLSEEKER_DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLE contacts ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_num TEXT, age TEXT)
        String createTable = ("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + KEY_NAME + " TEXT, " + KEY_PHONE_NUM + " TEXT, " +  KEY_USER_AGE + " TEXT)");

        //Note for self- getReadableDatabase() for grabing data. getWritableDatabase for inserting, updating, or delete data
        db.execSQL(createTable);
        //SQLiteDatabase database = this.getWritableDatabase();

        //query to insert

        //Create Freelancer table
        String createFreelancerTable = ("CREATE TABLE " + FREELANCER_TABLE_NAME + " (" + FREELANCER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FREELANCER_NAME + " TEXT, " + FREELANCER_EXP + " TEXT, " + FREELANCER_STAR_REVIEW + " TEXT)");
        db.execSQL(createFreelancerTable);
    }
    @Override
    //updates table
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drops older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FREELANCER_TABLE_NAME);
        //Creates table again
        onCreate(db);
    }

    public void addUser(String name, String phone_num, String age){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PHONE_NUM, phone_num);
        values.put(KEY_USER_AGE, age);


        db.insert(TABLE_NAME, null, values);
    }
    public void addFreelancer(String freelancer_name, String freelancer_exp, String review_stars) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FREELANCER_NAME, freelancer_name);
        values.put(FREELANCER_EXP, freelancer_exp);
        values.put(FREELANCER_STAR_REVIEW, review_stars);

        db.insert(FREELANCER_TABLE_NAME, null, values);
    }

    public ArrayList<ModalUser>fetchUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        //if looking for just name put condition in second parameter where null is
        //* mean all columns. Replace * with column name for just that column. use , for multi
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        ArrayList<ModalUser> arrayList = new ArrayList<ModalUser>();

        while(cursor.moveToNext()){
            ModalUser modaluser = new ModalUser();
            //Get Int because first column is ID num
            modaluser.id = cursor.getInt(0);
            modaluser.name=cursor.getString(1);
            modaluser.phone_num= cursor.getString(2);
            modaluser.age=cursor.getString(3);

            arrayList.add(modaluser);

        }
        return arrayList;
    }
}
