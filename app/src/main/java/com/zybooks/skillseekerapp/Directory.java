package com.zybooks.skillseekerapp;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import com.zybooks.skillseekerapp.databinding.DirectoryPageBinding;


public class Directory extends AppCompatActivity {

    DirectoryPageBinding binding;
//    private DiscoverFragment dfrag;
//    private HomeFragment Hfrag;
//    private MessagesFragment Mfrag;
//    private PostFragment Pfrag;
//    private SettingsFragment Sfrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_page);


        Toolbar toolbar = findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);

//        dfrag = new DiscoverFragment();
//        Hfrag = new HomeFragment();
//        Mfrag = new MessagesFragment();
//        Pfrag = new PostFragment();
//        Sfrag = new SettingsFragment();
//
//        // Add DiscoverFragment to FrameLayout
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frameLayout, dfrag)
//                .commit();

        binding = DirectoryPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment fragment = null;

            if (item.getItemId() == R.id.Home) {
                fragment = new HomeFragment();
            } else if (item.getItemId() == R.id.Discover) {
                fragment = new DiscoverFragment();
            } else if (item.getItemId() == R.id.Messages) {
                fragment = new MessagesFragment();
            } else if (item.getItemId() == R.id.Post) {
                fragment = new PostFragment();
            } else if (item.getItemId() == R.id.Profile) {
                fragment = new ProfileFragment();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.SearchButton);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handles any search text changes
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle other toolbar item clicks here
        return super.onOptionsItemSelected(item);
    }
}


/*
public HomeFragment getHomeFragment() {
    return Hfrag;
}

public MessagesFragment getMessagesFragment() {
    return Mfrag;
}

public PostFragment getPostFragment() {
    return Pfrag;
}

public SettingsFragment getSettingsFragment() {
    return Sfrag;
} */

