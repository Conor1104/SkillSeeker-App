package com.zybooks.skillseekerapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.zybooks.skillseekerapp.R;
import java.util.ArrayList;
import java.util.List;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;


public class HomeFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private JobAdapter jobAdapter;
    private List<Job> jobList;
    private List<Freelancer> freelancerList;
    private SSDataBaseHelper dbHelper;

    private Button filterButton;
    private Spinner job_category;
    private SwitchCompat UserOrFreelancer;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            Log.d("HomeFragment", "onCreate called");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("HomeFragment", "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //filterButton = view.findViewById(R.id.filter); //// Find the filter button
        UserOrFreelancer = view.findViewById(R.id.UserOrFreelancer); //Finds the switch button
        //Job Category Selector
        job_category = view.findViewById(R.id.job_options_home);

        // Set an item selected listener on the spinner
        job_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int item;
                    Log.d("HomeFragment", "onItemSelected called:" + parent.getItemAtPosition(position).toString()); //Checking which category gets called
                    String selectedFilter = parent.getItemAtPosition(position).toString();
                    filterJobs(selectedFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        UserOrFreelancer.setOnCheckedChangeListener((SwitchCompat, isChecked) -> {
                    if (isChecked) {
                        showFreelancers(UserOrFreelancer);
                    }
                    else {
                        fetchJobs();
                    }
                });

/*
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filterCriteria = "Automotive";
                filterJobs(filterCriteria);
            }
        });
        */

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.job_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        job_category.setAdapter(adapter);



        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        jobList = new ArrayList<>();
        freelancerList = new ArrayList<>();
        jobAdapter = new JobAdapter(jobList, getContext());
        recyclerView.setAdapter(jobAdapter);


        dbHelper = new SSDataBaseHelper();
        fetchJobs();


        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
        return view; // return the inflated view
    }


    private void showFreelancers(SwitchCompat UserOrFreelancer) {

            //boolean showFreelancers = UserOrFreelancer.isChecked();
        if (UserOrFreelancer.isChecked()) {
            // Show freelancers
            Log.d("HomeFragment", "showFreelancers called");
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            //freelancerList = new ArrayList<>();
            db.collection("freelancers").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    if (freelancerList!= null) {
                        freelancerList.clear();
                    }
                    //freelancerList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Freelancer freelancer = document.toObject(Freelancer.class);
                        freelancerList.add(freelancer);
                        //Freelancer freelancer = document.toObject(Freelancer.class);
                        //Freelancer freelancer = document.toObject(Freelancer.class);
                        freelancerList.add(freelancer);
                    }
                    jobAdapter.notifyDataSetChanged();
                }
                else {

                    Log.e("HomeFragment", "Error fetching freelancers", task.getException());
                    // Handle the error
                }
            });
        }
        else fetchJobs();

    }

    private void filterJobs(String criteria) {
        FirebaseFirestore fj = FirebaseFirestore.getInstance();
        fj.collection("jobs").whereEqualTo("job_category", criteria).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                jobList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Job job = document.toObject(Job.class);
                    jobList.add(job);
                }
                jobAdapter.notifyDataSetChanged();
            } else {
                Log.e("HomeFragment", "Error fetching jobs", task.getException());
                // Handle the error
            }
        });
    }

    private void fetchJobs() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("jobs").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                jobList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Job job = document.toObject(Job.class);
                    jobList.add(job);
                }
                jobAdapter.notifyDataSetChanged();
            } else {
                Log.e("HomeFragment", "Error fetching jobs", task.getException());
                // Handle the error
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }
    }

    public  class JobViewHolder extends RecyclerView.ViewHolder {

        Button contactButton;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

//            contactButton = itemView.findViewById(R.id.contactButton);

//            contactButton.setOnClickListener(v -> {
//                Log.d("JobViewHolder", "Contact button clicked");
//
//                // Navigate to MessagesFragment
//                Job job = jobList.get(getAdapterPosition());
//                String posterUserId = job.getPosterUserId();
//                Log.d("JobViewHolder", "Poster UserId: " + posterUserId);
//
//
//                Bundle bundle = new Bundle();
//                bundle.putString("posterUserId", posterUserId);
//
//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                MessagesFragment messagesFragment = new MessagesFragment();
//                messagesFragment.setArguments(bundle);
//
//                Log.d("HomeFragment", "Navigating to MessagesFragment");
//
//                activity.getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.main_container, messagesFragment) // Replace with your fragment container ID
//                        .addToBackStack(null)
//                        .commit();
//
//                Log.d("HomeFragment", "Transaction committed");
//                Log.d("JobViewHolder", "Navigated to MessagesFragment");
//            });
        }
    }
}

