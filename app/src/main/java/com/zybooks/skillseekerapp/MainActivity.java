package com.zybooks.skillseekerapp;

import androidx.appcompat.app.AppCompatActivity;
import com.zybooks.skillseekerapp.ModalFreelancer;
import com.zybooks.skillseekerapp.ModalUser;

import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
//For XML stuff
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SSDataBaseHelper dbHelper;
    private EditText nameInput;
    private EditText phoneInput;
    private EditText ageInput;
    private Button UserSubmitButton;
    private EditText freelancerNameInput;
    private EditText freelancerExpInput;
    private EditText freelancerReviewInput;
    private Button freelancerSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SSDataBaseHelper stuff
        //object of database class is created
        dbHelper= new SSDataBaseHelper(this);

        //finds the XML Data by id
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        ageInput = findViewById(R.id.ageInput);
        UserSubmitButton = findViewById(R.id.UserSubmitButton);

        freelancerNameInput = findViewById(R.id.freelancerNameInput);
        freelancerExpInput = findViewById(R.id.freelancerExpInput);
        freelancerReviewInput = findViewById(R.id.freelancerReviewInput);
        freelancerSubmitButton = findViewById(R.id.freelancerSubmitButton);



        //Listens for UserSubmitButton click and checks for if data is filled in or not
        UserSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String phone = phoneInput.getText().toString();
                String age = ageInput.getText().toString();

                //Error handling
                if (!name.isEmpty() && !phone.isEmpty() && !age.isEmpty()) {
                    dbHelper.addUser(name, phone, age);
                    Toast.makeText(MainActivity.this, "User added", Toast.LENGTH_SHORT).show();
                    clearInputs();
                } else {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        freelancerSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String freelancer_name = freelancerNameInput.getText().toString();
                String experience = freelancerExpInput.getText().toString();
                String review_stars = freelancerReviewInput.getText().toString();

                if (!freelancer_name.isEmpty() && !experience.isEmpty() && !review_stars.isEmpty()) {
                    dbHelper.addFreelancer(freelancer_name, experience, review_stars );
                    Toast.makeText(MainActivity.this, "User added", Toast.LENGTH_SHORT).show();
                    clearInputs();
                } else {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Test for fetching and logging users
        ArrayList<ModalUser> userData = dbHelper.fetchUser();
        for (ModalUser user : userData) {
            Log.d("User info", "ID: " + user.user_id + " Name: " + user.name + " Phone Num: " + user.phone_num + " Age: " + user.age);
        }

        // Test: fetch and log freelancers
        ArrayList<ModalFreelancer> freelancerData = dbHelper.fetchFreelancer();
        for (ModalFreelancer freelancer : freelancerData) {
            Log.d("Freelancer info", "ID: " + freelancer.freelancer_id + " Name: " + freelancer.name + " Experience: " + freelancer.experience + " Star Review: " + freelancer.starReview);
        }

        //Changes data in the data set. Changing phone num example
        /* *WORK IN PROGRESS*
        ModalUser user_modal = new ModalUser();
        user_modal.user_id = 1;
        user_modal.phone_num = "123";
        dbHelper.updateUser(user_modal);

         */
    }
        private void clearInputs() {
            nameInput.setText("");
            phoneInput.setText("");
            ageInput.setText("");
        }

        private void clearFreelancerInputs() {
            freelancerNameInput.setText("");
            freelancerExpInput.setText("");
            freelancerReviewInput.setText("");
        }
        //test dummy
        //calls function and data is passed to addUser In SSDataBaseHelper

        //Example for adding users and Freelancers
        //SS_USER_DATA_BASE.addUser("Will", "23525637" , "23");
        //SS_USER_DATA_BASE.addFreelancer("Mr. Freelancer", "678493349" , "5");

        //ArrayList<ModalUser> data= dbHelper.fetchUser();
        //Test displays print of data pulled from data set in log. This fetches user data
        /*for(int i = 0; i < data.size(); i++){
            Log.d("User info ", "Name " + data.get(i).name + " Phone Num " + data.get(i).phone_num + " Age " + data.get(i).age);
        }*/

}
