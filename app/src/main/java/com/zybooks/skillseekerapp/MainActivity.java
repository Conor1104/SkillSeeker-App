package com.zybooks.skillseekerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
//Firebase
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;




//login_register_page connected
public class MainActivity extends AppCompatActivity {

    //private SSDataBaseHelper dbHelper;
    private FirebaseFirestore db; // Declare FirebaseFirestore variable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_register_page);

        // Initialize the database helper
        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    public void goto_login_page (View view){
        Intent intent = new Intent (this, Login_Register.class);
        startActivity(intent);
    } // Goes to main login page (Login's Back Button)
    // Method to navigate to the main activity
    public void gotoActivity_Register(View view) {
        Intent intent = new Intent(this, Activity_Register.class);
        startActivity(intent);
    }
    public void goto_directory(View view){
        Intent intent = new Intent(this,Directory.class);
        startActivity(intent);
    }
}