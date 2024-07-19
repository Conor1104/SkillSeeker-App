package com.zybooks.skillseekerapp;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {
    private List<Job> jobList;
    private Context context;
    private FirebaseFirestore db;
    private static final String TAG = "JobAdapter";

    public JobAdapter(List<Job> jobList, Context context) {
        this.jobList = jobList;
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_posting, parent, false);
        return new JobViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.nameTextView.setText(job.getName());
        holder.emailTextView.setText(job.getEmail());
        holder.phoneTextView.setText(job.getPhone());
        holder.jobTitleTextView.setText(job.getJob_title());
        holder.cityTextView.setText(job.getCity());
        holder.descriptionTextView.setText(job.getDescription());
        holder.dateTextView.setText(job.getDate());
        Log.e(TAG, "userId is null or empty" + job.getUser_Id());
        // Initialize StarView elements
        StarView star1 = holder.itemView.findViewById(R.id.star1);
        StarView star2 = holder.itemView.findViewById(R.id.star2);
        StarView star3 = holder.itemView.findViewById(R.id.star3);
        StarView star4 = holder.itemView.findViewById(R.id.star4);
        StarView star5 = holder.itemView.findViewById(R.id.star5);

        // Fetch and set star rating
        fetchStarRating(job.getUser_Id(), star1, star2, star3, star4, star5);

//        holder.contactButton.setOnClickListener(v -> {
//            // Navigate to MessagesFragment
//            Bundle bundle = new Bundle();
//            bundle.putString("posterUserId", job.getPosterUserId());
//            AppCompatActivity activity = (AppCompatActivity) context;
//            MessagesFragment messagesFragment = new MessagesFragment();
//            messagesFragment.setArguments(bundle);
//
//            activity.getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.frameLayout, messagesFragment)
//                    .addToBackStack(null)
//                    .commit();
//        });
        holder.reviewButton.setOnClickListener(v -> {
        // Navigate to ReviewingProfilePage
                Intent intent = new Intent(context, ReviewingProfilePage.class);
                String posterUserId = job.getUser_Id();
                intent.putExtra(ReviewingProfilePage.EXTRA_POSTER_USER_ID, posterUserId);
                Log.e(TAG, "userId is null or empty" + posterUserId);
                context.startActivity(intent);

        });

    }


    private void fetchStarRating(String userId, StarView star1, StarView star2, StarView star3, StarView star4, StarView star5) {
        if (userId == null || userId.isEmpty()) {
            //Handling the case where userId is null or empty
            //Log.e(TAG, "userId is null or empty" + userId);
            resetStarViews(star1, star2, star3, star4, star5);
            return;
        }

        Log.d(TAG, "Fetching star rating for userId: " + userId);

        db.collection("users").document(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists() && document.contains("star_review")) {
                    int starReview = document.getLong("star_review").intValue();
                    Log.d(TAG, "Star review found in 'users': " + starReview);
                    updateStarViews(starReview, star1, star2, star3, star4, star5);
                } else {
                    // If not found in "users", check "freelancers"
                    db.collection("freelancers").document(userId).get().addOnCompleteListener(freelancerTask -> {
                        if (freelancerTask.isSuccessful()) {
                            DocumentSnapshot freelancerDoc = freelancerTask.getResult();
                            if (freelancerDoc.exists() && freelancerDoc.contains("star_review")) {
                                int starReview = freelancerDoc.getLong("star_review").intValue();
                                Log.d(TAG, "Star review found in 'freelancers': " + starReview);
                                updateStarViews(starReview, star1, star2, star3, star4, star5);
                            } else {
                                Log.e(TAG, "Star review not found in either 'users' or 'freelancers'");
                                resetStarViews(star1, star2, star3, star4, star5);
                            }
                        } else {
                            Log.e(TAG, "Error fetching freelancer: " + freelancerTask.getException().getMessage());
                            resetStarViews(star1, star2, star3, star4, star5);
                        }
                    });
                }
            } else {
                Log.e(TAG, "Error fetching user: " + task.getException().getMessage());
                resetStarViews(star1, star2, star3, star4, star5);
            }
        });
    }

    private void updateStarViews(int starReview, StarView star1, StarView star2, StarView star3, StarView star4, StarView star5) {
        star1.setSelected(starReview >= 1);
        star2.setSelected(starReview >= 2);
        star3.setSelected(starReview >= 3);
        star4.setSelected(starReview >= 4);
        star5.setSelected(starReview >= 5);
    }

    private void resetStarViews(StarView star1, StarView star2, StarView star3, StarView star4, StarView star5) {
        star1.setSelected(false);
        star2.setSelected(false);
        star3.setSelected(false);
        star4.setSelected(false);
        star5.setSelected(false);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, phoneTextView, jobTitleTextView, cityTextView, descriptionTextView, dateTextView;
        Button contactButton, reviewButton;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            jobTitleTextView = itemView.findViewById(R.id.jobTitleTextView);
            cityTextView = itemView.findViewById(R.id.cityTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
//            contactButton = itemView.findViewById(R.id.contactButton);
            reviewButton = itemView.findViewById(R.id.reviewButton);
        }
    }
}