package com.zybooks.skillseekerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;
import com.zybooks.skillseekerapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

//    BottomNavigationView bottomNavigationView;
//    HomeFragment homeFragment = new HomeFragment();
//    MyBooksFragment myBooksFragment = new MyBooksFragment();
//    ProfileFragment profileFragment = new ProfileFragment();
//    MoreFragment moreFragment = new MoreFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
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
                fragment = new PostFragment();
            } else if (item.getItemId() == R.id.Messages) {
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