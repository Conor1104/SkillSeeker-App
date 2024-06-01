package com.zybooks.skillseekerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SSDataBaseHelper stuff
        //object of database class is created
        SSDataBaseHelper SS_USER_DATA_BASE= new SSDataBaseHelper(this);
        //test dummy
        //calls function and data is passed to addUser In SSDataBaseHelper
        SS_USER_DATA_BASE.addUser("Will", "23525637" , "23");

        SS_USER_DATA_BASE.addFreelancer("Mr. Freelancer", "678493349" , "5");


    }
}