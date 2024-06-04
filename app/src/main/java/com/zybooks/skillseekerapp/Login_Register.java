/*BUGS
* - BUG is users/freelancers can sign in with mismatched user names or passwords.
* Will P
* */

package com.zybooks.skillseekerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//xml stuff
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Login_Register extends AppCompatActivity {

    private SSDataBaseHelper dbHelper;
    private EditText UsernameFreelancerInput;
    private EditText passwordInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);

        //Helps with login buttons function and when called login check will happen to test to see if the user exist in the databse
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Untilize the database helper class
        dbHelper = new SSDataBaseHelper(this);

        UsernameFreelancerInput = findViewById(R.id.userName_FreelancerName_Input);
        passwordInput = findViewById(R.id.passwordIntput);
    }

    //Checks if login for if valid or unvaild user
    private void login(View view) {
        String username_freelancername = UsernameFreelancerInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        //Checking if the user exist id Database
        boolean userExist = checkUser(username_freelancername, password);

        // Checking if the freelancer exists in the freelancer Database
        boolean freelancerExist = checkFreelancer(username_freelancername, password);

        if (userExist) {//user is in database
            //Userexist
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            goto_directory(view);

        }
        else if (freelancerExist){
            // Freelancer exists in freelancer database
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            goto_directory(view);
        }
        else {//user is not in database or input info is wrong
            Toast.makeText(Login_Register.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean checkUser(String usernameFreelancerInput, String passwordInput) {
        ArrayList<ModalUser> userList = dbHelper.fetchUser();//useing the user datatable
        ArrayList<ModalFreelancer> freelancerList = dbHelper.fetchFreelancer();//useing the freelancer datatable

        // Iterates over the user list/datatabe to find a match for the provided username/Freelancer name and password
        for (ModalUser user : userList) {
            if (user.user_email.equals(usernameFreelancerInput) && user.user_password.equals(passwordInput)) {
                // User with matching username/Freelancer name and password found
                return true;
            }
        }

        // No user with matching name/Freelancer name and password found
        return false;

    }

    private boolean checkFreelancer(String usernameFreelancerInput, String passwordInput) {
        ArrayList<ModalFreelancer> freelancerList = dbHelper.fetchFreelancer();

        // Iterate over the freelancer list/datatabe to find a match for the provided username and password
        for (ModalFreelancer freelancer : freelancerList) {
            if (freelancer.name.equals(usernameFreelancerInput) && freelancer.freelancerPassword.equals(passwordInput)) {
                return true;
            }
        }
        return false;
    }


    public void goto_login_page(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    } // Will go to login page

    public void goto_directory(View view) {
        Intent intent = new Intent(this, Directory.class);
        startActivity(intent);
    }//Will go to Directory

}

