
package com.zybooks.skillseekerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

import androidx.annotation.Nullable;

public class SSDataBaseHelper extends SQLiteOpenHelper {

    //USER_DATA_TABLE
    private static final String SKILLSEEKER_DATABASE = "SkillSeekerDatabase";
    //If trying to add a new data set to the DATABASE_VERSION must be incremented to see new change
    private static final int DATABASE_VERSION= 6;

    //Columns
    private static final String USER_TABLE_NAME = "Contacts";
    private static final String KEY_ID = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NUM = "phone_num";
    private static final String KEY_USER_AGE = "age";

    private static final String KEY_USER_PASSWORD = "user_password";

    private static final String KEY_USER_EMAIL = "user_email";

    //USER_DATA_TABLE

    //FREELANCERS_DATA_TABEL
    private static final String FREELANCER_TABLE_NAME= "freelancer_contacts";
    private static final String FREELANCER_ID= "freelancer_id";
    private static final String FREELANCER_NAME = "freelancer_name";
    private static final String FREELANCER_EXP= "freelancer_exp";
    private static final String FREELANCER_STAR_REVIEW= "review_stars";

    private static final String FREELANCER_PASSWORD= "freelancer_password";

    //FREELANCERS_DATA_TABEL


    public SSDataBaseHelper(@Nullable Context context) {
        super(context, SKILLSEEKER_DATABASE, null, DATABASE_VERSION);
    }

    @Override
    //Method creates both the User Table and Freelancer Table
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLE contacts ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_num TEXT, age TEXT)
        String createUserTable = ("CREATE TABLE " + USER_TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + KEY_NAME + " TEXT, " + KEY_PHONE_NUM + " TEXT, " +  KEY_USER_AGE + " TEXT, " + KEY_USER_PASSWORD + " TEXT," + KEY_USER_EMAIL + " TEXT)");

        //Note for self- getReadableDatabase() for grabing data. getWritableDatabase for inserting, updating, or delete data
        db.execSQL(createUserTable);
        //SQLiteDatabase database = this.getWritableDatabase();

        //query to insert

        //Create Freelancer table
        String createFreelancerTable = ("CREATE TABLE " + FREELANCER_TABLE_NAME + " (" + FREELANCER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FREELANCER_NAME + " TEXT, " + FREELANCER_EXP + " TEXT, " + FREELANCER_STAR_REVIEW + " TEXT, " + FREELANCER_PASSWORD + " TEXT)");
        db.execSQL(createFreelancerTable);
    }
    @Override
    //updates table
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drops older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FREELANCER_TABLE_NAME);
        //Creates table again
        onCreate(db);
    }

    //Method for adding Users to the User Database
    public void addUser(String name, String phone_num, String age, String user_password, String user_email){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PHONE_NUM, phone_num);
        values.put(KEY_USER_AGE, age);
        values.put(KEY_USER_PASSWORD, user_password);
        values.put(KEY_USER_EMAIL, user_email);


        db.insert(USER_TABLE_NAME, null, values);
    }
    //Method for adding Freelancers to the User Database
    public void addFreelancer(String freelancer_name, String freelancer_exp, String review_stars, String freelancer_password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FREELANCER_NAME, freelancer_name);
        values.put(FREELANCER_EXP, freelancer_exp);
        values.put(FREELANCER_STAR_REVIEW, review_stars);
        values.put(FREELANCER_PASSWORD, freelancer_password);

        db.insert(FREELANCER_TABLE_NAME, null, values);
    }

    public ArrayList<ModalUser> fetchUser() {
        ArrayList<ModalUser> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + USER_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Helps find the the retrievable values in User datatable with the help of the cursor
        if (cursor.moveToFirst()) {
            do {
                ModalUser user = new ModalUser();
                user.user_id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                user.name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
                user.phone_num = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE_NUM));
                user.age = cursor.getString(cursor.getColumnIndexOrThrow(KEY_USER_AGE));
                user.user_password = cursor.getString(cursor.getColumnIndexOrThrow(KEY_USER_PASSWORD));
                user.user_email = cursor.getString(cursor.getColumnIndexOrThrow(KEY_USER_EMAIL));

                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }

    //Method that updates data in the User Database
    public void updateUser(ModalUser modaluser){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //DATA we want to update

        // Check if the name is modified
        if (modaluser.name != null) {
            values.put(KEY_NAME, modaluser.name);
        }

        // Check if the phone number is modified
        if (modaluser.phone_num != null) {
            values.put(KEY_PHONE_NUM, modaluser.phone_num);
        }

        // Check if the age is modified
        if (modaluser.age != null) {
            values.put(KEY_USER_AGE, modaluser.age);
        }
        if (modaluser.user_password != null) {
            values.put(KEY_USER_PASSWORD, modaluser.user_password);
        }
        if (modaluser.user_email != null) {
            values.put(KEY_USER_EMAIL, modaluser.user_email);
        }
        /*
        values.put(KEY_PHONE_NUM, modaluser.phone_num);
        values.put(KEY_NAME, modaluser.name);
        values.put(KEY_USER_AGE, modaluser.age);
         */
        db.update(USER_TABLE_NAME, values, KEY_ID + " = " + modaluser.user_id, null);

    }
    public ArrayList<ModalFreelancer> fetchFreelancer() {
        ArrayList<ModalFreelancer> freelancerList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + FREELANCER_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

       //Helps find the the retrievable values in freelancer datatable with the help of the cursor
        if (cursor.moveToFirst()) {
            do {
                ModalFreelancer freelancer = new ModalFreelancer();
                freelancer.freelancer_id = cursor.getInt(cursor.getColumnIndexOrThrow(FREELANCER_ID));
                freelancer.name = cursor.getString(cursor.getColumnIndexOrThrow(FREELANCER_NAME));
                freelancer.experience = cursor.getString(cursor.getColumnIndexOrThrow(FREELANCER_EXP));
                freelancer.starReview = cursor.getString(cursor.getColumnIndexOrThrow(FREELANCER_STAR_REVIEW));
                freelancer.freelancerPassword = cursor.getString(cursor.getColumnIndexOrThrow(FREELANCER_PASSWORD));
                freelancerList.add(freelancer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return freelancerList;
    }

    //Method to update Freelanceer data in datatable *WORK IN PROGRESS*
    public void updateFreelancer(ModalFreelancer freelancer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FREELANCER_NAME, freelancer.name);
        values.put(FREELANCER_EXP, freelancer.experience);
        values.put(FREELANCER_STAR_REVIEW, freelancer.starReview);
        values.put(FREELANCER_PASSWORD, freelancer.freelancerPassword);

        db.update(FREELANCER_TABLE_NAME, values, FREELANCER_ID + " = " + freelancer.freelancer_id, null);
    }
}


