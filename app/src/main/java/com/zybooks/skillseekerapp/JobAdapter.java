package com.zybooks.skillseekerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.zybooks.skillseekerapp.Job;


import androidx.annotation.NonNull;

import java.util.List;



public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {
    private List<Job> jobList;

    public JobAdapter(List<Job> jobList) {
        this.jobList = jobList;
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
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, phoneTextView, jobTitleTextView, cityTextView, descriptionTextView,dateTextView;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            jobTitleTextView = itemView.findViewById(R.id.jobTitleTextView);
            cityTextView = itemView.findViewById(R.id.cityTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            //Initialize other views similarly
        }
    }
}
