package com.zybooks.skillseekerapp;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewingProfilePage extends AppCompatActivity {

    private static final String TAG = "ReviewingProfilePage";
    public static final String EXTRA_POSTER_USER_ID = "posterUserId";
    public static final String USER_OR_FREELANCERID = "active_id";
    private String active_id;

    private String posterUserId;

    private TextView experinceEditText, freelancer_email, freelancer_phone, nameFreelancer, experience_HeaderText, age_headerText, stars_HeaderText, nameTextView, phoneTextView, emailTextView, ageTextView, experienceTextView;
    private EditText starsEditText;
    private StarView[] starViews = new StarView[5];
    private Button saveButton_Freelancers;

    private Button saveButton_Users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent_active_id = getIntent();
        active_id = intent_active_id.getStringExtra(USER_OR_FREELANCERID);


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
        freelancer_email = findViewById(R.id.freelancer_email_text);
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
        db.collection("users").document(posterUserId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Display user profile information
                    nameTextView.setText(document.getString("name"));
                    phoneTextView.setText(document.getString("phone_num"));
                    emailTextView.setText(document.getString("user_email"));
                    ageTextView.setText(document.getString("age"));

                    List<Long> reviewsLong = (List<Long>) document.get("reviews");
                    List<Double> reviews = new ArrayList<>();
                    if (reviewsLong != null) {
                        for (Long review : reviewsLong) {
                            reviews.add(review.doubleValue()); // Convert Long to Double
                        }
                    }
                    double averageRating = calculateAverageRating(reviews);
                    updateStarRating(averageRating);

                    // Hide EditText fields for user profile
                    starsEditText.setVisibility(View.VISIBLE);

                    starsEditText.setText(String.valueOf(averageRating)); //Converts int to String

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

                    List<Long> reviewsLong = (List<Long>) document.get("reviews");
                    List<Double> reviews = new ArrayList<>();
                    if (reviewsLong != null) {
                        for (Long review : reviewsLong) {
                            reviews.add(review.doubleValue()); // Convert Long to Double
                        }
                    }
                    double averageRating = calculateAverageRating(reviews);
                    updateStarRating(averageRating);

                    starsEditText.setText(String.valueOf(averageRating)); // Converts int to String

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

    private void updateStarRating(double starRating) {
        int fullStars = (int) Math.floor(starRating);
        boolean hasHalfStar = starRating > fullStars;

        for (int i = 0; i < 5; i++) {
            if (i < fullStars) {
                starViews[i].setSelected(true); // Select full star
            } else if (i == fullStars && hasHalfStar) {
                starViews[i].setSelected(true); // Set half star if needed
            } else {
                starViews[i].setSelected(false); // Deselect star
            }
        }
    }

//    private void updateStarViews(int starReview) {
//        if (starViews == null) return;
//        for (int i = 0; i < 5; i++) {
//            if (starViews[i] != null) {
//                if (i < starReview) {
//                    starViews[i].setSelected(true);
//                } else {
//                    starViews[i].setSelected(false);
//                }
//            }
//        }
//        if (starsEditText != null) starsEditText.setText(String.valueOf(starReview));
//    }

    private double calculateAverageRating(List<Double> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Double rating : reviews) {
            total += rating;
        }
        return total / reviews.size();
    }

    private void saveStarRating() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        double newStarRating;
        try {
            newStarRating = Double.parseDouble(starsEditText.getText().toString());
            if (newStarRating < 0 || newStarRating > 5) {
                Toast.makeText(this, "Star rating must be between 0 and 5", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid star rating", Toast.LENGTH_SHORT).show();
            return;
        }

        if (active_id == null) {
            Toast.makeText(this, "You need an account to make a review", Toast.LENGTH_SHORT).show();
        } else if (!active_id.equals(posterUserId)) {
            db.collection("users").document(posterUserId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<Long> reviewsLong = (List<Long>) document.get("reviews");
                        List<Double> reviews = new ArrayList<>();
                        if (reviewsLong != null) {
                            for (Long review : reviewsLong) {
                                reviews.add(review.doubleValue()); // Convert Long to Double
                            }
                        }
                        reviews.add(newStarRating); // Adding a Double to a List<Double>
                        double averageRating = calculateAverageRating(reviews);


                        Map<String, Object> updates = new HashMap<>();
                        updates.put("reviews", reviews);
                        updates.put("averageRating", averageRating); // Update the average rating field
                        db.collection("users").document(posterUserId).update(updates).addOnCompleteListener(updateTask -> {
                            if (updateTask.isSuccessful()) {
                                updateStarRating(averageRating);
                            } else {
                                Toast.makeText(this, "Error updating rating: " + updateTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "Error fetching user profile: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "You cannot rate your own account", Toast.LENGTH_SHORT).show();
        }
    }
}

//    private void saveStarRating() {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        int newStarRating;
//        try {
//            newStarRating = Integer.parseInt(starsEditText.getText().toString());
//            if (newStarRating < 0 || newStarRating > 5) {
//                Toast.makeText(this, "Star rating must be between 0 and 5", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        } catch (NumberFormatException e) {
//            Toast.makeText(this, "Invalid star rating", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if(active_id == null){
//            Toast.makeText(this, "You need an account to make a review", Toast.LENGTH_SHORT).show();
//        }
//        else if (!active_id.equals(posterUserId)) {
//            db.collection("users").document(posterUserId).get().addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        List<Long> reviews = (List<Long>) document.get("reviews");
//                        if (reviews == null) {
//                            reviews = new ArrayList<>();
//                        }
//                        reviews.add((long) newStarRating);
//                        int averageRating = calculateAverageRating(reviews);
//                        Map<String, Object> updates = new HashMap<>();
//                        updates.put("reviews", reviews);
//                    }
//                        }
//                    });
//        }
//        else{
//            Toast.makeText(this, "You can not rate your own account", Toast.LENGTH_SHORT).show();
//        }
//    }
//}

//Updated