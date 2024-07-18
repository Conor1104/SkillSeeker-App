package com.zybooks.skillseekerapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.zybooks.skillseekerapp.Job;


import androidx.annotation.NonNull;

import java.util.List;



public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {
    private List<Job> jobList;
    private Context context;

    public JobAdapter(List<Job> jobList, Context context) {
        this.jobList = jobList;
        this.context = context;
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
        // Set other fields similarly

        holder.contactButton.setOnClickListener(v -> {
            // Navigate to MessagesFragment
            Bundle bundle = new Bundle();
            bundle.putString("posterUserId", job.getPosterUserId());

            AppCompatActivity activity = (AppCompatActivity) context;
            MessagesFragment messagesFragment = new MessagesFragment();
            messagesFragment.setArguments(bundle);

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, messagesFragment)
                    .addToBackStack(null)
                    .commit();
        });

    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, phoneTextView, jobTitleTextView, cityTextView, descriptionTextView,dateTextView;
        Button contactButton;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            jobTitleTextView = itemView.findViewById(R.id.jobTitleTextView);
            cityTextView = itemView.findViewById(R.id.cityTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);

            contactButton = itemView.findViewById(R.id.contactButton);
            //Initialize other views similarly
        }
    }
}
