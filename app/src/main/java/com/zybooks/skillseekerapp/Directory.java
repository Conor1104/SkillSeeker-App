package com.zybooks.skillseekerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zybooks.skillseekerapp.databinding.DirectoryPageBinding;

public class Directory extends AppCompatActivity {

    DirectoryPageBinding binding;
    //    private DiscoverFragment dfrag;
    //    private HomeFragment Hfrag;
    //    private MessagesFragment Mfrag;
    //private PostFragment Pfrag;
    private ProfileFragment profileFragment;
    //    private SettingsFragment Sfrag;
    private String user_or_freelancerID;
    private boolean UserProfile_detected;

    private static final String TAG = "Directory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.directory_page);
        binding = DirectoryPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent != null) {
            user_or_freelancerID = intent.getStringExtra("USER_ID");
        }
        if (user_or_freelancerID != null && !user_or_freelancerID.isEmpty()) {
            //Check both freelancers and users tables
            checkFreelancerProfile(user_or_freelancerID);
        } else {
            //For guest user, handled navigation without profile access
            UserProfile_detected = false;
            replaceFragment(new HomeFragment());
        }


        //Pfrag = new PostFragment();

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

                if (UserProfile_detected==true) {
                    if (profileFragment == null) {
                        profileFragment = ProfileFragment.newInstance(user_or_freelancerID);
                    }
                    fragment = profileFragment;
                } else {
                    Toast.makeText(this, "Profile not found", Toast.LENGTH_SHORT).show();
                }
            }

            if (fragment != null) {
                replaceFragment(fragment);
                return true;
            }
            return false;
        });

    }


//        dfrag = new DiscoverFragment();
//        Hfrag = new HomeFragment();
//        Mfrag = new MessagesFragment();
//Pfrag = new PostFragment();
//        Sfrag = new SettingsFragment();
//
//        // Add DiscoverFragment to FrameLayout
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frameLayout, dfrag)
//                .commit();
/*
        //binding = DirectoryPageBinding.inflate(getLayoutInflater());
        // setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.Home:
                    fragment = new HomeFragment();
                    break;
                case R.id.Discover:
                    fragment = new DiscoverFragment();
                    break;
                case R.id.Messages:
                    fragment = new MessagesFragment();
                    break;
                case R.id.Post:
                    fragment = new PostFragment();
                    break;
                case R.id.Profile:
                    if (profileFragment == null) {
                        profileFragment = ProfileFragment.newInstance("initialUserID");
                    }
                    fragment = profileFragment;
                    break;
            }
            /*
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
            return true; */


    private void checkFreelancerProfile(String user_or_freelancerID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("freelancers").document(user_or_freelancerID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    UserProfile_detected = true;
                    replaceFragment(new HomeFragment());
                } else {
                    // Freelancer profile not found but continues checking for user profile
                    checkUserProfile(user_or_freelancerID);
                }
            } else {
                Log.e(TAG, "Error fetching freelancer profile: ", task.getException());
                // Handle error scenario
                UserProfile_detected = false;
                replaceFragment(new HomeFragment());
            }
        });
    }

    private void checkUserProfile(String user_or_freelancerID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user_or_freelancerID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    UserProfile_detected = true;
                    replaceFragment(new HomeFragment());
                } else {
                    // Neither freelancer nor user profile found
                    UserProfile_detected = false;
                    replaceFragment(new HomeFragment());
                }
            } else {
                Log.e(TAG, "Error fetching user profile: ", task.getException());
                //Handle error scenario
                UserProfile_detected = false;
                replaceFragment(new HomeFragment());
            }
        });
    }


    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.SearchButton);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Handle search query submission
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Handles any search text changes
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle other toolbar item clicks here
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

