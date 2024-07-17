package com.zybooks.skillseekerapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.core.view.MenuProvider;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.zybooks.skillseekerapp.R;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

public class DiscoverFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "DiscoverFragment";
    private RecyclerView recyclerView;
    //private JobAdapter jobAdapter;
    //private List<Job> jobList;
    private SSDataBaseHelper dbHelper;
    private SearchView searchView;


    private String mParam1;
    private String mParam2;

    public DiscoverFragment() {
        // Required empty public constructor
    }

//    public static DiscoverFragment newInstance(String param1, String param2) {
//        DiscoverFragment fragment = new DiscoverFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setHasOptionsMenu(true); // Enable options menu
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //jobList = new ArrayList<>();
        //jobAdapter = new JobAdapter(jobList);
        //recyclerView.setAdapter(jobAdapter);

        dbHelper = new SSDataBaseHelper();

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "Search query submitted: " + query);
                searchJobs(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "Search query text changed: " + newText); // Add this line
                // Optionally handle text change events
                return false;
            }
        });

        return view;
    }


    private void searchJobs(String query) {
        Log.d(TAG, "searchJobs called with query: " + query);
        dbHelper.getDb().collection("jobs")
                .whereEqualTo("job_category", query)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Task successful");
                        //jobList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            //Job job = document.toObject(Job.class);
                            //jobList.add(job);
                            //Log.d(TAG, "Job found: " + job.getJob_category());
                        }
                        //jobAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, "Error getting documents: ", task.getException());
                        // Handle the error
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}



