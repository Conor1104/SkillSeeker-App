
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

//Firebase
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Login_Register extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText UsernameFreelancerInput;
    private EditText passwordInput;


    //View.OnFocusChangeListener
    //UsernameFreelancerInput.setSelectAllOnFocus(true)

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

        //spot

        UsernameFreelancerInput = findViewById(R.id.userName_FreelancerName_Input);
        passwordInput = findViewById(R.id.passwordIntput);
    }

    //Checks if login for if valid or unvaild user
    private void login(View view) {
        String username_freelancername = UsernameFreelancerInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        //checking if the user exists in the database
        checkUser(username_freelancername, password, view);
    }

    private void checkUser(String usernameFreelancerInput, String passwordInput, View view) {
        db.collection("users")
                .whereEqualTo("name", usernameFreelancerInput)
                .whereEqualTo("user_password", passwordInput)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {

                        //user exists in the database
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String user_or_freelancerID = document.getId();//Gets the USERS ID
                            goto_directory(view, user_or_freelancerID);
                            break;
                        }
                    } else {
                        //checking if the freelancer exists in the freelancer database
                        checkFreelancer(usernameFreelancerInput, passwordInput, view);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Login_Register.this, "Error checking user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void checkFreelancer(String usernameFreelancername, String password, View view) {
        db.collection("freelancers")
                .whereEqualTo("freelancer_name", usernameFreelancername)
                .whereEqualTo("freelancer_password", password)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        //freelancer exists in the database
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String user_or_freelancerID = document.getId();//Gets the FREELANCERS ID
                            goto_directory(view, user_or_freelancerID);
                            break;
                        }
                    } else {
                        Toast.makeText(Login_Register.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Login_Register.this, "Error checking freelancer: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    public void goto_mainactivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    } // Will go to mainactivity page

    public void goto_directory(View view, String user_or_freelancerID) {
        Intent intent = new Intent(this, Directory.class);
        intent.putExtra("USER_ID", user_or_freelancerID);//Pushes either the user or Freelancers ID to the directory so functions can access the users ID
        startActivity(intent);
    }//Will go to Directory

}

