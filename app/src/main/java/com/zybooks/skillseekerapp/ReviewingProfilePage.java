package com.zybooks.skillseekerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReviewingProfilePage extends AppCompatActivity {

    private static final String TAG = "ReviewingProfilePage";
    public static final String EXTRA_POSTER_USER_ID = "posterUserId";

    private String posterUserId;

    private TextView experinceEditText, freelancer_email ,freelancer_phone , nameFreelancer ,experience_HeaderText, age_headerText, stars_HeaderText,nameTextView, phoneTextView, emailTextView, ageTextView, experienceTextView;
    private EditText starsEditText;
    private StarView[] starViews = new StarView[5];
    private Button saveButton_Freelancers;

    private Button saveButton_Users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewing_profile_page);


        // Get the posterUserId from the Intent
        Intent intent = getIntent();
        posterUserId = intent.getStringExtra(EXTRA_POSTER_USER_ID);


        // Initialize views
        nameTextView = findViewById(R.id.profile_name_text);
        phoneTextView = findViewById(R.id.profile_phone_text);
        emailTextView = findViewById(R.id.profile_email_text);
        ageTextView = findViewById(R.id.profile_age_text);
        experienceTextView = findViewById(R.id.freelancer_experience_text);
        starsEditText = findViewById(R.id.profile_stars_edit);
        //Save button(Changes to the info in profile will be saved)
        saveButton_Users = findViewById(R.id.save_button_users);
        saveButton_Freelancers = findViewById(R.id.save_button_freelancers);

        // Initialize StarView array
        starViews[0] = findViewById(R.id.star1);
        starViews[1] = findViewById(R.id.star2);
        starViews[2] = findViewById(R.id.star3);
        starViews[3] = findViewById(R.id.star4);
        starViews[4] = findViewById(R.id.star5);

        //Headers
        experience_HeaderText = findViewById(R.id.experience_HeaderText);
        age_headerText = findViewById(R.id.age_headerText);
        stars_HeaderText = findViewById(R.id.stars_HeaderText);

        //EditText boxes
        //All in fragment_profile.xml
        nameFreelancer = findViewById(R.id.profile_name_freelancer);
        freelancer_phone = findViewById(R.id.freelancer_phone_text);
        freelancer_email= findViewById(R.id.freelancer_email_text);
        //starsEditText =view_prof.findViewById(R.id.profile_stars);


        // Handle save button click
        saveButton_Users.setOnClickListener(v -> saveStarRating());
        saveButton_Freelancers.setOnClickListener(v -> saveStarRating());


        //Initially set all views to GONE
        setUserViewsVisibility(View.GONE);
        setFreelancerViewsVisibility(View.GONE);
        experience_HeaderText.setVisibility(View.GONE);
        age_headerText.setVisibility(View.GONE);
        saveButton_Freelancers.setVisibility(View.GONE);
        saveButton_Users.setVisibility(View.GONE);


        // Fetch and display profile information
        fetchProfileInfo();

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
    private void fetchProfileInfo() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Log.e(TAG, "userId is null or empty" + posterUserId);
        db.collection("users").document(posterUserId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Display user profile information
                    nameTextView.setText(document.getString("name"));
                    phoneTextView.setText(document.getString("phone_num"));
                    emailTextView.setText(document.getString("user_email"));
                    ageTextView.setText(document.getString("age"));

                    int starReview = document.getLong("star_review").intValue();
                    updateStarViews(starReview);

                    // Hide EditText fields for user profile
                    starsEditText.setVisibility(View.VISIBLE);

                    starsEditText.setText(String.valueOf(starReview));//Converts int to String

                    // Show user views and hide freelancer views
                    setUserViewsVisibility(View.VISIBLE);
                    setFreelancerViewsVisibility(View.GONE);
                    experienceTextView.setVisibility(View.GONE);
                    experience_HeaderText.setVisibility(View.GONE);
                    saveButton_Freelancers.setVisibility(View.GONE);
                    age_headerText.setVisibility(View.VISIBLE);
                    saveButton_Users.setVisibility(View.VISIBLE);

                } else {
                    // If user data not found, fetch freelancer profile
                    fetchFreelancerProfile();
                }
            } else {
                Toast.makeText(this, "Error fetching user profile: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchFreelancerProfile() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("freelancers").document(posterUserId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Display freelancer profile information
                    nameFreelancer.setText(document.getString("freelancer_name"));
                    freelancer_phone.setText(document.getString("freelancer_phone"));
                    freelancer_email.setText(document.getString("freelancer_email"));
                    experienceTextView.setText(document.getString("freelancer_exp"));

                    int starReview = document.getLong("star_review").intValue();
                    updateStarViews(starReview);

                    starsEditText.setText(String.valueOf(starReview));//Converts int to String
                    // Hide EditText fields for freelancer profile
                    starsEditText.setVisibility(View.VISIBLE);
                    setFreelancerViewsVisibility(View.VISIBLE);
                    phoneTextView.setVisibility(View.VISIBLE);
                    setUserViewsVisibility(View.GONE);
                    age_headerText.setVisibility(View.GONE);
                    saveButton_Users.setVisibility(View.GONE);
                    experienceTextView.setVisibility(View.VISIBLE);
                    experience_HeaderText.setVisibility(View.VISIBLE);
                    saveButton_Freelancers.setVisibility(View.VISIBLE);


                } else {
                    Toast.makeText(this, "User profile not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Error fetching freelancer profile: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setUserViewsVisibility(int visibility) {
        phoneTextView.setVisibility(visibility);
        nameTextView.setVisibility(visibility);
        emailTextView.setVisibility(visibility);
        ageTextView.setVisibility(visibility);
    }

    private void setFreelancerViewsVisibility(int visibility) {
        nameFreelancer.setVisibility(visibility);
        freelancer_phone.setVisibility(visibility);
        freelancer_email.setVisibility(visibility);
    }
    private void updateStarViews(int starReview) {
        if (starViews == null) return;
        for (int i = 0; i < 5; i++) {
            if (starViews[i] != null) {
                if (i < starReview) {
                    starViews[i].setSelected(true);
                } else {
                    starViews[i].setSelected(false);
                }
            }
        }
        if (starsEditText != null) starsEditText.setText(String.valueOf(starReview));
    }

    private void saveStarRating() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        int newStarRating;
        try {
            newStarRating = Integer.parseInt(starsEditText.getText().toString());
            if (newStarRating < 0 || newStarRating > 5) {
                Toast.makeText(this, "Star rating must be between 0 and 5", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid star rating", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("users").document(posterUserId).update("star_review", newStarRating)
                .addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(this, "Star rating updated successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        db.collection("freelancers").document(posterUserId).update("star_review", newStarRating)
                                .addOnCompleteListener(task2 -> {
                                    if (task2.isSuccessful()) {
                                        Toast.makeText(this, "Star rating updated successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(this, "Error updating star rating: " + task2.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
    }
}