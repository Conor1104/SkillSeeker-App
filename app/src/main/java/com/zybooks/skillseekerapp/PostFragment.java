package com.zybooks.skillseekerapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.zybooks.skillseekerapp.R;

///**
 //* A simple {@link Fragment} subclass.
 //* Use the {@link PostFragment#newInstance} factory method to
 //* create an instance of this fragment.
 //*/
import com.google.firebase.firestore.FirebaseFirestore;
public class PostFragment extends Fragment {


    private String freelancer_or_user_name;

    private String user_or_freelancer_email;
    private String user_or_freelancer_phone;

    private static final String USER_OR_FREELANCER_ID = "user_or_freelancer_id";
    private String user_or_freelancer_id;

    private Spinner job_category;

    private EditText job_title;

    private EditText date_of_post;

    private EditText city;

    private EditText job_description;

    private Button submit_post_button;

    private FirebaseFirestore db; //Firebase instance
    private SSDataBaseHelper databaseHelper; //Database helper instance

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public PostFragment() {
        //Required empty public constructor
}


    ///**
    // * Use this factory method to create a new instance of
    // * this fragment using the provided parameters.
    // *
    // * @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     //* @return A new instance of fragment PostFragment.
     //*/
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String user_or_freelancer_id) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(USER_OR_FREELANCER_ID , user_or_freelancer_id);
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user_or_freelancer_id = getArguments().getString(USER_OR_FREELANCER_ID);
            //mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();
        databaseHelper = new SSDataBaseHelper(); //Initialize database helper
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        //fetchUserorFeelancerData();;
        //user_or_freelancer_id= pulled id;
        //freelancer_or_user_name = pulled name;
        //user_or_freelancer_email = pulled email;
        //user_or_freelancer_phone = pulled pone;


        //finds the XML Data by id
        job_category = view.findViewById(R.id.job_options_Spinner);
        job_title = view.findViewById(R.id.editTextNameOfJob);
        date_of_post = view.findViewById(R.id.editTextDate);
        city = view.findViewById(R.id.editTextCity);
        job_description = view.findViewById(R.id.Job_description);
        //button
        submit_post_button = view.findViewById(R.id.button_submit);

        // Populate spinner with job options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.job_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        job_category.setAdapter(adapter);

        // Check if user_or_freelancer_id is not null before fetching data
        if (user_or_freelancer_id != null) {
            fetchUserorFeelancerData();
        } else {
            //Where user_or_freelancer_id is null show an error message
        }

        submit_post_button.setOnClickListener(v -> {
            Log.d("PostFragment", "User or Freelancer ID: " + user_or_freelancer_id);
            if (user_or_freelancer_id != null) {
                String job = job_category.getSelectedItem().toString();
                String jobTitle = job_title.getText().toString();
                String date = date_of_post.getText().toString();
                String cityName = city.getText().toString();
                String description = job_description.getText().toString();

                //Call addJob method from SSDataBaseHelper to add the job to Firestore
                databaseHelper.addJob(user_or_freelancer_id, freelancer_or_user_name, user_or_freelancer_email, user_or_freelancer_phone, job, jobTitle, date, cityName, description);

                //clear input fields or show a success message
                clearInputFields();
                Toast.makeText(getContext(), "Post Submitted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getContext(), "Post Failed to Submitted", Toast.LENGTH_SHORT).show();
            }
            });


        return view;
    }

    private void fetchUserorFeelancerData() {
        db.collection("users").document(user_or_freelancer_id).get().addOnCompleteListener(username_task -> {
            if (username_task.isSuccessful()) {
                DocumentSnapshot document = username_task.getResult();
                //Log.d("PostFragment", "User or Freelancer ID: " + user_or_freelancer_id);
                if (document.exists()) {
                    freelancer_or_user_name = document.getString("name");
                    user_or_freelancer_email = document.getString("user_email");
                    user_or_freelancer_phone = document.getString("phone_num");
                }
                else{
                    fetchFreelancerData();
                }
            }
            else{
                fetchFreelancerData();
            }
        });
    }
    private void fetchFreelancerData() {
        //Fetch freelancer data from freelancers table
        db.collection("freelancers").document(user_or_freelancer_id).get().addOnCompleteListener(freelancer_username_task -> {
            if (freelancer_username_task.isSuccessful()) {
                DocumentSnapshot document = freelancer_username_task.getResult();
                if (document.exists()) {
                    freelancer_or_user_name = document.getString("freelancer_name");
                    user_or_freelancer_email = document.getString("freelancer_email");
                    user_or_freelancer_phone = document.getString("freelancer_phone");
                }
            }
        });
    }


    private void clearInputFields() {
        //Clear input fields after successful submission
        job_title.getText().clear();
        date_of_post.getText().clear();
        city.getText().clear();
        job_description.getText().clear();
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

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }
    */
}
