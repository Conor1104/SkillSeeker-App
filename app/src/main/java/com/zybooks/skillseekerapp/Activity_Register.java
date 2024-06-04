package com.zybooks.skillseekerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
//For XML stuff
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_Register extends AppCompatActivity {

    private SSDataBaseHelper dbHelper;
    private EditText nameInput;
    private EditText phoneInput;
    private EditText ageInput;

    private EditText userPassword;

    private EditText userEmail;
    private Button UserSubmitButton;
    private EditText freelancerNameInput;
    private EditText freelancerExpInput;
    private EditText freelancerReviewInput;
    private Button freelancerSubmitButton;
    private EditText freelancerPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //SSDataBaseHelper stuff
        //object of database class is created
        dbHelper= new SSDataBaseHelper(this);

        //finds the XML Data by id
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        ageInput = findViewById(R.id.ageInput);
        userPassword = findViewById(R.id.userPassword);
        userEmail = findViewById(R.id.userEmail);
        UserSubmitButton = findViewById(R.id.UserSubmitButton);

        freelancerNameInput = findViewById(R.id.freelancerNameInput);
        freelancerExpInput = findViewById(R.id.freelancerExpInput);
        freelancerReviewInput = findViewById(R.id.freelancerReviewInput);
        freelancerSubmitButton = findViewById(R.id.freelancerSubmitButton);
        freelancerPasswordInput = findViewById(R.id.freelancerPasswordInput);



        //Listens for UserSubmitButton click and checks for if data is filled in or not
        UserSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String phone = phoneInput.getText().toString();
                String age = ageInput.getText().toString();
                String user_password = userPassword.getText().toString();
                String user_email = userEmail.getText().toString();

                //Error handling
                if (!name.isEmpty() && !phone.isEmpty() && !age.isEmpty() &&!user_password.isEmpty() && !user_email.isEmpty()) {
                    dbHelper.addUser(name, phone, age, user_password, user_email);
                    Toast.makeText(Activity_Register.this, "User added", Toast.LENGTH_SHORT).show();
                    clearInputs();
                    //Will take the User to the login page to login to there new account
                    goto_login_page();
                } else {
                    Toast.makeText(Activity_Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        freelancerSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String freelancer_name = freelancerNameInput.getText().toString();
                String experience = freelancerExpInput.getText().toString();
                String review_stars = freelancerReviewInput.getText().toString();
                String freelancer_password = freelancerPasswordInput.getText().toString();

                if (!freelancer_name.isEmpty() && !experience.isEmpty() && !review_stars.isEmpty() && !freelancer_password.isEmpty()) {
                    dbHelper.addFreelancer(freelancer_name, experience, review_stars,freelancer_password );
                    Toast.makeText(Activity_Register.this, "User added", Toast.LENGTH_SHORT).show();
                    clearInputs();
                    //Will take the Freelancer to the login page to login to there new account
                    goto_login_page();
                }
                else {
                    Toast.makeText(Activity_Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Test for fetching and logging users
        ArrayList<ModalUser> userData = dbHelper.fetchUser();
        for (ModalUser user : userData) {
            Log.d("User info", "ID: " + user.user_id + " Name: " + user.name + " Phone Num: " + user.phone_num + " Age: " + user.age + " Password: " + user.user_password + " Email: " + user.user_email);
        }

        // Test: fetch and log freelancers
        ArrayList<ModalFreelancer> freelancerData = dbHelper.fetchFreelancer();
        for (ModalFreelancer freelancer : freelancerData) {
            Log.d("Freelancer info", "ID: " + freelancer.freelancer_id + " Name: " + freelancer.name + " Experience: " + freelancer.experience + " Star Review: " + freelancer.starReview + " Freelancer Password: " + freelancer.freelancerPassword);
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
        userPassword.setText("");
        userEmail.setText("");
    }

    private void clearFreelancerInputs() {
        freelancerNameInput.setText("");
        freelancerExpInput.setText("");
        freelancerReviewInput.setText("");
        freelancerPasswordInput.setText("");
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

    //Not Button
    public void goto_login_page(){
        Intent intent = new Intent (this, Login_Register.class);
        startActivity(intent);
    }

    //Button
    public void goto_main(View view){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    } // Goes to main login screen (Method For Back Button)

}

