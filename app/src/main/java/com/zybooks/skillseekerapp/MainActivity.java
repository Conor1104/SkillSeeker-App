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
        SSDataBaseHelper SSDBhelper= new SSDataBaseHelper(this);
        //test dummy
        //calls function and data is passed to addUser In SSDataBaseHelper
        SSDBhelper.addUser("Will", "23525637");
    }
}
