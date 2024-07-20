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

    private ProfileFragment profileFragment;

    private PostFragment postFragment;
    private MessagesFragment messagesFragment;
    private static String user_or_freelancerID;
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
            replaceFragment(new HomeFragment(), false);
        }







        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;

            if (item.getItemId() == R.id.Home) {
                fragment = new HomeFragment();
            } else if (item.getItemId() == R.id.Discover) {
                fragment = new DiscoverFragment();
            }
            /*else if (item.getItemId() == R.id.Messages) {
                if(messagesFragment== null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    messagesFragment = new MessagesFragment();
                    fragmentTransaction.add(R.id.main_container,messagesFragment);
                    fragmentTransaction.commit();
                }
                fragment = messagesFragment;
                }
*/

                //This is trying to create a new instance of the fragment
            else if (item.getItemId() == R.id.Post) {
                if (UserProfile_detected==true) {
                    if (postFragment == null) {
                        postFragment = PostFragment.newInstance(user_or_freelancerID);
                    }
                    fragment = postFragment;
                }
                else {
                    Toast.makeText(this, "Make account to post job", Toast.LENGTH_SHORT).show();

                }
            } else if (item.getItemId() == R.id.Profile) {

                if (UserProfile_detected==true) {
                    if (profileFragment == null) {
                        profileFragment = ProfileFragment.newInstance(user_or_freelancerID);
                    }
                    fragment = profileFragment;
                }
                else {
                    Toast.makeText(this, "Profile not found, Please Register", Toast.LENGTH_SHORT).show();

                    Intent register = new Intent(this, Activity_Register.class);
                    //register.putExtra("goto_directory","goto_directory");
                    startActivity(register);

                }
            }



            if (fragment != null) {
                replaceFragment(fragment, true);
                return true;
            }
            return false;
        });

    }



    private void checkFreelancerProfile(String user_or_freelancerID) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("freelancers").document(user_or_freelancerID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    UserProfile_detected = true;
                    replaceFragment(new HomeFragment(), false);
                } else {
                    //Freelancer profile not found but continues checking for user profile
                    checkUserProfile(user_or_freelancerID);
                }
            } else {
                Log.e(TAG, "Error fetching freelancer profile: ", task.getException());
                // Handle error scenario
                UserProfile_detected = false;
                replaceFragment(new HomeFragment(), false);
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
                    replaceFragment(new HomeFragment(), false);
                } else {
                    // Neither freelancer nor user profile found
                    UserProfile_detected = false;
                    replaceFragment(new HomeFragment(), false);
                }
            } else {
                Log.e(TAG, "Error fetching user profile: ", task.getException());
                //Handle error scenario
                UserProfile_detected = false;
                replaceFragment(new HomeFragment(), false);
            }
        });
    }

        public static String reviewProfileBypassID() {
            String intent_active_id=user_or_freelancerID;
            return intent_active_id;

        }



    private void replaceFragment(Fragment fragment, boolean addToBackStack) {

        Log.d(TAG, "Replacing fragment with " + fragment.getClass().getSimpleName());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle other toolbar item clicks here
        return super.onOptionsItemSelected(item);
    }

}


