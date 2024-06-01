package com.zybooks.skillseekerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.zybooks.skillseekerapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SSDataBaseHelper stuff
        //object of database class is created
        SSDataBaseHelper SS_USER_DATA_BASE= new SSDataBaseHelper(this);
        //test dummy
        //calls function and data is passed to addUser In SSDataBaseHelper

        //Example for adding users and Freelancers
        //SS_USER_DATA_BASE.addUser("Will", "23525637" , "23");
        //SS_USER_DATA_BASE.addFreelancer("Mr. Freelancer", "678493349" , "5");

        ArrayList<ModalUser> data= SS_USER_DATA_BASE.fetchUser();

        //Test displays print of data from data set in log
        for(int i = 0; i < data.size(); i++){
            Log.d("User info ", "Name " + data.get(i).name + " Phone Num " + data.get(i).phone_num + " Age " + data.get(i).age);
        }



        // Conors code

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment fragment = null;

            if (item.getItemId() == R.id.Home) {
                fragment = new HomeFragment();
            } else if (item.getItemId() == R.id.Discover) {
                fragment = new DiscoverFragment();
            } else if (item.getItemId() == R.id.Post) {
                fragment = new MessagesFragment();
            } else if (item.getItemId() == R.id.Settings) {
                fragment = new SettingsFragment();
            }

            if (fragment != null) {
                replaceFragment(fragment);

            }
            return true;

        });

    }
    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}

