package com.zybooks.skillseekerapp;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "ProfileFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String USER_OR_FREELANCER_ID = "user_or_freelancer_id";

    private StarView[] starViews = new StarView[5]; //Array to hold StarView references
    //private boolean UserProfile_detectected;

    private String user_or_freelancer_id;

    // Save button
    private Button saveButton_Freelancers;

    private Button saveButton_Users;

    //Headers
    private TextView age_Header;

    private TextView experience_Header;

    //user EditText
    private EditText nameuserEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText ageEditText;

    //freelancer EditText

    private EditText nameFreelancerEditText;
    private EditText experinceEditText;

    private EditText freelancer_emailEditText;

    private EditText freelancer_phoneEditText;
    private TextView starsEditText;
    private TextView starsHeader;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String user_or_freelancer_id) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(USER_OR_FREELANCER_ID , user_or_freelancer_id);
        ;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user_or_freelancer_id = getArguments().getString(USER_OR_FREELANCER_ID);
            //userProfile_detected = getArguments().getBoolean(USER_PROFILE_DETECTED);
            //Log.d(TAG, "ID received: " + user_or_freelancer_id ); //For Debug checking
            if (user_or_freelancer_id == null){
                goto_mainactivity(); //redirects to the app main activity
                Toast.makeText(getContext(), "Create an account to have a Profile", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view_prof = inflater.inflate(R.layout.fragment_profile, container, false);

        //Initialize StarView array
        starViews[0] = view_prof.findViewById(R.id.star1);
        starViews[1] = view_prof.findViewById(R.id.star2);
        starViews[2] = view_prof.findViewById(R.id.star3);
        starViews[3] = view_prof.findViewById(R.id.star4);
        starViews[4] = view_prof.findViewById(R.id.star5);


        //Headers
        experience_Header = view_prof.findViewById(R.id.experience_HeaderText);
        age_Header = view_prof.findViewById(R.id.age_headerText);
        starsHeader = view_prof.findViewById(R.id.stars_HeaderText);

        //EditText boxes
        nameuserEditText = view_prof.findViewById(R.id.profile_name_user);
        phoneEditText = view_prof.findViewById(R.id.profile_phone);
        emailEditText = view_prof.findViewById(R.id.profile_email);
        ageEditText = view_prof.findViewById(R.id.profile_age);
        starsEditText = view_prof.findViewById(R.id.profile_stars);
        //All in fragment_profile.xml
        nameFreelancerEditText = view_prof.findViewById(R.id.profile_name_freelancer);
        freelancer_phoneEditText = view_prof.findViewById(R.id.freelancer_phone);
        freelancer_emailEditText = view_prof.findViewById(R.id.freelancer_email);
        experinceEditText = view_prof.findViewById(R.id.freelancer_experience);
        //starsEditText =view_prof.findViewById(R.id.profile_stars);


        //Save button(Changes to the info in profile will be saved)
        saveButton_Freelancers = view_prof.findViewById(R.id.save_button_freelancers);
        saveButton_Users = view_prof.findViewById(R.id.save_button_users);
        saveButton_Freelancers.setOnClickListener(v -> saveProfile());
        saveButton_Users.setOnClickListener(v -> saveProfile());

        //Initially set all views to GONE
        setUserViewsVisibility(View.GONE);
        setFreelancerViewsVisibility(View.GONE);
        experience_Header.setVisibility(View.GONE);
        age_Header.setVisibility(View.GONE);
        saveButton_Freelancers.setVisibility(View.GONE);
        saveButton_Users.setVisibility(View.GONE);

        //Fetch and display user profile information using the userId
        fetchUserProfile();


        return view_prof;
    }

    private void updateStarRating(int starRating) {
        for (int i = 0; i < 5; i++) {
            if (i < starRating) {
                starViews[i].setSelected(true); //Select star
            } else {
                starViews[i].setSelected(false); //Deselect star
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }



    private void fetchUserProfile() {//Pulling Data from Database. Try's to pull user data first then if that doesn't exist it try's looking in Freelancer data
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user_or_freelancer_id).get().addOnCompleteListener(task -> { //Looks for user by hash ID
            if(task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    //UserProfile_detectected = true; //Indicates this is a user profile

                    String age = document.getString("age");
                    String name = document.getString("name");
                    String phone = document.getString("phone_num");
                    String email = document.getString("user_email");
                    int star_review;
                    star_review = document.getLong("star_review").intValue();
                    updateStarRating(star_review);

                    //Update UI with user profile information
                    nameuserEditText.setText(name);
                    phoneEditText.setText(phone);
                    emailEditText.setText(email);
                    ageEditText.setText(age);
                    starsEditText.setText(String.valueOf(star_review)); //Converts int to String

                    // Show user views and hide freelancer views
                    setUserViewsVisibility(View.VISIBLE);
                    setFreelancerViewsVisibility(View.GONE);
                    experience_Header.setVisibility(View.GONE);
                    saveButton_Freelancers.setVisibility(View.GONE);
                    age_Header.setVisibility(View.VISIBLE);
                    saveButton_Users.setVisibility(View.VISIBLE);

                    //starsEditText.setVisibility(View.GONE);
                }
                else {
                    //If user data not found try's fetching Freelancer profile
                    fetchFreelancerProfile(user_or_freelancer_id);
                }
            }
            else {
                Toast.makeText(getContext(), "Error fetching user: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                //Log.e(TAG, "Error fetching freelancer: ", task.getException());
            }
        });
    }

    private void fetchFreelancerProfile(String user_or_freelancer_id) {//looks for Freelancer data for their profile
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Fetch user profile
        db.collection("freelancers").document(this.user_or_freelancer_id).get().addOnCompleteListener(task -> {//Looks for freelancer by hash ID
            //Log.e(TAG, "Freelacner found: " + this.user_or_freelancer_id);
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    //UserProfile_detectected = false; //Indicates this is a freelancer profile

                    String experience = document.getString("freelancer_exp");
                    String name = document.getString("freelancer_name");
                    String phone = document.getString("freelancer_phone");
                    String email = document.getString("freelancer_email");
                    int star_review = (Objects.requireNonNull(document.getLong("star_review"))).intValue();
                    updateStarRating(star_review);
                    String stars = document.getString("review_stars");

                    //Update UI with user profile information
                    nameFreelancerEditText.setText(name);
                    experinceEditText.setText(experience);
                    freelancer_phoneEditText.setText(phone);
                    freelancer_emailEditText.setText(email);
                    //starsEditText.setText(String.valueOf(star_review));//Converts int to String
                    //starsEditText.setText("Star Rating: "+stars);


                    // Show freelancer views and hide user views(boxes)
                    setFreelancerViewsVisibility(View.VISIBLE);
                    setUserViewsVisibility(View.GONE);
                    age_Header.setVisibility(View.GONE);
                    saveButton_Users.setVisibility(View.GONE);
                    experience_Header.setVisibility(View.VISIBLE);
                    saveButton_Freelancers.setVisibility(View.VISIBLE);

                    //Log.e(TAG, "Freelacner found: " + this.user_or_freelancer_id);

                } else {
                    Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
                    //Log.e(TAG, "User not found: " + this.user_or_freelancer_id);
                }
            } else {
                Toast.makeText(getContext(), "Error fetching freelancer: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                //Log.e(TAG, "Error fetching user: ", task.getException());
            }
        });
    }
    private void setUserViewsVisibility(int visibility) {
        nameuserEditText.setVisibility(visibility);
        phoneEditText.setVisibility(visibility);
        emailEditText.setVisibility(visibility);
        ageEditText.setVisibility(visibility);
    }

    private void setFreelancerViewsVisibility(int visibility) {
        nameFreelancerEditText.setVisibility(visibility);
        freelancer_phoneEditText.setVisibility(visibility);
        freelancer_emailEditText.setVisibility(visibility);
        experinceEditText.setVisibility(visibility);
    }

    private void saveProfile() {//Saves the profile data of users and freelancers and updates the DB with that data
        FirebaseFirestore db = FirebaseFirestore.getInstance();//calling the DB
        Map<String, Object> profileData = new HashMap<>();
/*
        try {
            int star_review = Integer.parseInt(starsEditText.getText().toString());

            //Validate star rating
            if (!isValidStarRating(star_review)) {
                Toast.makeText(getContext(), "Star rating must be between 0 and 5", Toast.LENGTH_SHORT).show();
                return; //Exit the method to prevent saving invalid data
            }

 */

            //Log.d(TAG, user_or_freelancer_id);
            db.collection("users").document(this.user_or_freelancer_id).get().addOnCompleteListener(user_found_task -> {
                DocumentSnapshot user_documentID = user_found_task.getResult();
                if (user_documentID.exists()) {

                    //Save user data
                    String name = nameuserEditText.getText().toString();
                    String phone = phoneEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String age = ageEditText.getText().toString();


                    profileData.put("name", name);
                    profileData.put("phone_num", phone);
                    profileData.put("user_email", email);
                    profileData.put("age", age);
                    //profileData.put("star_review", star_review);

                    db.collection("users").document(user_or_freelancer_id).update(profileData).addOnCompleteListener(user_found_task2 -> {//Conformation and updates data in DB
                        if (user_found_task2.isSuccessful()) {
                            Toast.makeText(getContext(), "Your profile was updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error updating your profile: " + user_found_task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {//For freelancers profile data to update
                    //Save freelancer profile
                    String name = nameFreelancerEditText.getText().toString();
                    String phone = freelancer_phoneEditText.getText().toString();
                    String email = freelancer_emailEditText.getText().toString();
                    String experience = experinceEditText.getText().toString();
                    //int star_review = Integer.parseInt(starsEditText.getText().toString());

                    profileData.put("freelancer_name", name);
                    profileData.put("freelancer_phone", phone);
                    profileData.put("freelancer_email", email);
                    profileData.put("freelancer_exp", experience);
                    //profileData.put("star_review", star_review);

                    db.collection("freelancers").document(user_or_freelancer_id).update(profileData).addOnCompleteListener(freelancer_found_task -> {//Conformation and updates data in DB
                        if (freelancer_found_task.isSuccessful()) {
                            Toast.makeText(getContext(), "Your profile was updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error updating your Freelancer profile: " + freelancer_found_task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

    }
    private boolean isValidStarRating(int starRating) {
        return starRating >= 0 && starRating <= 5;
    }
    public void goto_mainactivity() {// Will go to mainactivity page
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}