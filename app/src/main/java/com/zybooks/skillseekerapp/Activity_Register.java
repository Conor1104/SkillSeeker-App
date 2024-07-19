package com.zybooks.skillseekerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
//For XML stuff
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class Activity_Register extends AppCompatActivity {

    private FirebaseFirestore db; //Firebase instance
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
    private EditText freelancerEmailInput;
    private EditText freelancerPhoneNumberInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //SSDataBaseHelper stuff
        //Initializes firebase database
        db = FirebaseFirestore.getInstance();

        //finds the XML Data by id
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        ageInput = findViewById(R.id.ageInput);
        userPassword = findViewById(R.id.userPassword);
        userEmail = findViewById(R.id.userEmail);
        UserSubmitButton = findViewById(R.id.UserSubmitButton);

        freelancerNameInput = findViewById(R.id.freelancerNameInput);
        freelancerExpInput = findViewById(R.id.freelancerExpInput);
        freelancerEmailInput = findViewById(R.id.freelancerEmail);
        //freelancerReviewInput = findViewById(R.id.freelancerReviewInput);
        freelancerPasswordInput = findViewById(R.id.freelancerPasswordInput);
        freelancerPhoneNumberInput = findViewById(R.id.freelancerPhone);
        freelancerSubmitButton = findViewById(R.id.freelancerSubmitButton);



        //Listens for UserSubmitButton click and checks for if data is filled in or not
        UserSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Input boxes
                String name = nameInput.getText().toString();
                String phone = phoneInput.getText().toString();
                String age = ageInput.getText().toString();
                String user_password = userPassword.getText().toString();
                String user_email = userEmail.getText().toString();

                //Error handling
                if (!name.isEmpty() && !phone.isEmpty() && !age.isEmpty() && !user_password.isEmpty() && !user_email.isEmpty()) {
                    addUserToFirestore(name, phone, age, user_password, user_email);//Calls the method to add the User
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
                //Input boxes
                String freelancer_name = freelancerNameInput.getText().toString();
                String experience = freelancerExpInput.getText().toString();
                String freelancer_email = freelancerEmailInput.getText().toString();
                //String review_stars = freelancerReviewInput.getText().toString();
                String freelancer_password = freelancerPasswordInput.getText().toString();
                String freelancer_phone = freelancerPhoneNumberInput.getText().toString() ;

                //Error handling
                if (!freelancer_name.isEmpty() && !experience.isEmpty() && !freelancer_email.isEmpty() && !freelancer_password.isEmpty() && !freelancer_phone.isEmpty()) {
                    addFreelancerToFirestore(freelancer_name, experience, freelancer_email, freelancer_password, freelancer_phone); //Calls the method to add the Freelancer
                    Toast.makeText(Activity_Register.this, "Freelancer added", Toast.LENGTH_SHORT).show();
                    clearInputs();
                    //Will take the Freelancer to the login page to login to there new account
                    goto_login_page();
                } else {
                    Toast.makeText(Activity_Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        //Test for fetching and logging users
        private void addUserToFirestore(String name, String phone, String age, String password, String email) {
            Map<String, Object> user = new HashMap<>(); //Data is Hashed(Security)
            user.put("name", name);
            user.put("phone_num", phone);
            user.put("age", age);
            user.put("user_password", password);
            user.put("user_email", email);
            user.put("star_review",0);


            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(Activity_Register.this, "User added", Toast.LENGTH_SHORT).show();
                        clearInputs();
                        goto_login_page();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Activity_Register.this, "Error adding user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }

        private void addFreelancerToFirestore(String name, String experience, String email, String password, String phone) {
            Map<String, Object> freelancer = new HashMap<>();
            freelancer.put("freelancer_name", name);
            freelancer.put("freelancer_exp", experience);
            freelancer.put("freelancer_email", email);
            //freelancer.put("review_stars", reviewStars);
            freelancer.put("freelancer_password", password);
            freelancer.put("freelancer_phone", phone);
            freelancer.put("star_review",0);


            db.collection("freelancers")
                    .add(freelancer)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(Activity_Register.this, "Freelancer added", Toast.LENGTH_SHORT).show();
                        clearFreelancerInputs();
                        goto_login_page();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Activity_Register.this, "Error adding freelancer: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });


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
    //calls function and data is passed to addUser In SSDataBaseHelper (OLD VER)

    //Example for adding users and Freelancers
    //SS_USER_DATA_BASE.addUser("Will", "23525637" , "23");
    //SS_USER_DATA_BASE.addFreelancer("Mr. Freelancer", "678493349" , "5");

    //ArrayList<ModalUser> data= dbHelper.fetchUser();
    //Test displays print of data pulled from data set in log. This fetches user data
        /*for(int i = 0; i < data.size(); i++){
            Log.d("User info ", "Name " + data.get(i).name + " Phone Num " + data.get(i).phone_num + " Age " + data.get(i).age);
        }*/

    //Button
    public void goto_login_page(){
        Intent intent = new Intent (this, Login_Register.class);
        startActivity(intent);
    }

    //Button
    public void goto_main(){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    } // Goes to main login screen (Method For Back Button)

    public void goto_guest_directory(View view) {
        Intent intent = new Intent(this, Directory.class);
        startActivity(intent);
    }





}

